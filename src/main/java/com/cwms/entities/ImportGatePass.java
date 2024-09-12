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
@Table(name = "cfimportgatepass")
@IdClass(ImportGatePassId.class)
public class ImportGatePass {

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
	@Column(name = "Gate_Pass_Id", length = 10, nullable = false)
	private String gatePassId;

	@Id
	@Column(name = "sr_no", nullable = false)
	private int srNo;

	@Column(name = "Vehicle_Gate_Pass_Id", length = 10)
	private String vehicleGatePassId;

	@Column(name = "CON_SR_NO", precision = 8, scale = 0)
	private BigDecimal conSrNo;

	@Column(name = "Profitcentre_Id", length = 6)
	private String profitcentreId;

	@Column(name = "Invoice_No", length = 16)
	private String invoiceNo;

	@Column(name = "Invoice_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date invoiceDate;

	@Column(name = "Gate_Pass_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date gatePassDate;

	@Column(name = "IGM_No", length = 10)
	private String igmNo;

	@Column(name = "IGM_LINE_NO", length = 7)
	private String igmLineNo;

	@Column(name = "IGM_Trans_Id", length = 10)
	private String igmTransId;

	@Column(name = "NOC_NO", length = 15)
	private String nocNo;

	@Column(name = "Noc_Validity_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date nocValidityDate;

	@Column(name = "Shift", length = 6)
	private String shift;

	@Column(name = "Trans_Type", length = 10)
	private String transType;

	@Column(name = "Importer_Name", length = 100)
	private String importerName;

	@Column(name = "importer_address1", length = 250)
	private String importerAddress1;

	@Column(name = "importer_address2", length = 100)
	private String importerAddress2;

	@Column(name = "importer_address3", length = 100)
	private String importerAddress3;

	@Column(name = "CHA", length = 25)
	private String cha;

	@Column(name = "Container_No", length = 11)
	private String containerNo;

	@Column(name = "Container_Size", length = 6)
	private String containerSize;

	@Column(name = "Container_Type", length = 6)
	private String containerType;

	@Column(name = "BOE", length = 20)
	private String boe;

	@Column(name = "BE_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date beDate;

	@Column(name = "Cargo_Value", precision = 15, scale = 3)
	private BigDecimal cargoValue;

	@Column(name = "Cargo_Duty", precision = 15, scale = 3)
	private BigDecimal cargoDuty;

	@Column(name = "BL_No", length = 20)
	private String blNo;

	@Column(name = "BL_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date blDate;

	@Column(name = "VIA_NO", length = 10)
	private String viaNo;

	@Column(name = "Commodity", length = 250)
	private String commodity;

	@Column(name = "Gross_Wt", precision = 15, scale = 3)
	private BigDecimal grossWt;

	@Column(name = "No_Of_Package")
	private int noOfPackage;

	@Column(name = "QTY_TAKEN_OUT", precision = 8, scale = 0)
	private BigDecimal qtyTakenOut;

	@Column(name = "Vehicle_Qty_Taken_Out", precision = 8, scale = 0)
	private BigDecimal vehicleQtyTakenOut;

	@Column(name = "GW_Taken_Out", precision = 8, scale = 3)
	private BigDecimal gwTakenOut;

	@Column(name = "Yard_Packages", precision = 18, scale = 3)
	private BigDecimal yardPackages;

	@Column(name = "Cell_Area_Allocated", precision = 18, scale = 3)
	private BigDecimal cellAreaAllocated;

	@Column(name = "Area_Released", precision = 8, scale = 3)
	private BigDecimal areaReleased;

	@Column(name = "Transporter_Status", length = 1)
	private String transporterStatus;

	@Column(name = "Transporter", length = 6)
	private String transporter;

	@Column(name = "Transporter_Name", length = 50)
	private String transporterName;

	@Column(name = "Vehicle_No", length = 15)
	private String vehicleNo;

	@Column(name = "Driver_Name", length = 50)
	private String driverName;

	@Column(name = "Comments", length = 150)
	private String comments;

	@Column(name = "Remarks", length = 250)
	private String remarks;

	@Column(name = "Gate_Out_Id", length = 10)
	private String gateOutId;

	@Column(name = "Gate_Out_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date gateOutDate;

	@Column(name = "SL", length = 10)
	private String sl;

	@Column(name = "Destuff_Line_Id", length = 5)
	private String destuffLineId;

	@Column(name = "Destuff_Id", length = 10)
	private String destuffId;

	@Column(name = "DRT", length = 1)
	private String drt;

	@Column(name = "GRN_No", length = 25)
	private String grnNo;

	@Column(name = "GRN_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date grnDate;

	@Column(name = "CIN_No", length = 25)
	private String cinNo;

	@Column(name = "CIN_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date cinDate;

	@Column(name = "Stamp_Duty", precision = 12, scale = 2)
	private BigDecimal stampDuty;

	@Column(name = "SPLGate_Out_Flag", length = 1)
	private String splGateOutFlag;

	@Column(name = "DPD_Flag", length = 1)
	private String dpdFlag;

	@Column(name = "DO_No", length = 30)
	private String doNo;

	@Column(name = "DO_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date doDate;

	@Column(name = "DO_Validity_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date doValidityDate;

	@Column(name = "OOC_No", length = 20)
	private String oocNo;

	@Column(name = "OOC_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date oocDate;

	@Column(name = "Loading_Start_date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date loadingStartDate;

	@Column(name = "Loading_End_date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date loadingEndDate;

	@Column(name = "Status", length = 1)
	private String status;

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

	@Column(name = "Yard_Location", length = 250)
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

	@Column(name = "Veh_Status", length = 1)
	private String vehStatus;

	@Column(name = "GatePass_Type", length = 5)
	private String gatePassType;

	@Column(name = "oth_party_Id", length = 10)
	private String othPartyId;

	@Column(name = "webCamPath", length = 120)
	private String webCamPath;

	@Column(name = "Scanner_Type", length = 60)
	private String scannerType;

	@Column(name = "ISO", length = 4)
	private String iso;

	@Column(name = "Actual_seal_no", length = 15)
	private String actualSealNo;

	@Column(name = "MTY_Yard_Location", length = 10)
	private String mtyYardLocation;
	
	@Column(name="Gate-Out_Qty")
	private int gateOutQty;

	public ImportGatePass() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public ImportGatePass(String companyId, String branchId, String finYear, String gatePassId, int srNo,
			String vehicleGatePassId, BigDecimal conSrNo, String profitcentreId, String invoiceNo, Date invoiceDate,
			Date gatePassDate, String igmNo, String igmLineNo, String igmTransId, String nocNo, Date nocValidityDate,
			String shift, String transType, String importerName, String importerAddress1, String importerAddress2,
			String importerAddress3, String cha, String containerNo, String containerSize, String containerType,
			String boe, Date beDate, BigDecimal cargoValue, BigDecimal cargoDuty, String blNo, Date blDate,
			String viaNo, String commodity, BigDecimal grossWt, int noOfPackage, BigDecimal qtyTakenOut,
			BigDecimal vehicleQtyTakenOut, BigDecimal gwTakenOut, BigDecimal yardPackages, BigDecimal cellAreaAllocated,
			BigDecimal areaReleased, String transporterStatus, String transporter, String transporterName,
			String vehicleNo, String driverName, String comments, String remarks, String gateOutId, Date gateOutDate,
			String sl, String destuffLineId, String destuffId, String drt, String grnNo, Date grnDate, String cinNo,
			Date cinDate, BigDecimal stampDuty, String splGateOutFlag, String dpdFlag, String doNo, Date doDate,
			Date doValidityDate, String oocNo, Date oocDate, Date loadingStartDate, Date loadingEndDate, String status,
			String createdBy, Date createdDate, String editedBy, Date editedDate, String approvedBy, Date approvedDate,
			String yardLocation, String yardBlock, String blockCellNo, String yardLocation1, String yardBlock1,
			String blockCellNo1, String vehStatus, String gatePassType, String othPartyId, String webCamPath,
			String scannerType, String iso, String actualSealNo, String mtyYardLocation, int gateOutQty) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.finYear = finYear;
		this.gatePassId = gatePassId;
		this.srNo = srNo;
		this.vehicleGatePassId = vehicleGatePassId;
		this.conSrNo = conSrNo;
		this.profitcentreId = profitcentreId;
		this.invoiceNo = invoiceNo;
		this.invoiceDate = invoiceDate;
		this.gatePassDate = gatePassDate;
		this.igmNo = igmNo;
		this.igmLineNo = igmLineNo;
		this.igmTransId = igmTransId;
		this.nocNo = nocNo;
		this.nocValidityDate = nocValidityDate;
		this.shift = shift;
		this.transType = transType;
		this.importerName = importerName;
		this.importerAddress1 = importerAddress1;
		this.importerAddress2 = importerAddress2;
		this.importerAddress3 = importerAddress3;
		this.cha = cha;
		this.containerNo = containerNo;
		this.containerSize = containerSize;
		this.containerType = containerType;
		this.boe = boe;
		this.beDate = beDate;
		this.cargoValue = cargoValue;
		this.cargoDuty = cargoDuty;
		this.blNo = blNo;
		this.blDate = blDate;
		this.viaNo = viaNo;
		this.commodity = commodity;
		this.grossWt = grossWt;
		this.noOfPackage = noOfPackage;
		this.qtyTakenOut = qtyTakenOut;
		this.vehicleQtyTakenOut = vehicleQtyTakenOut;
		this.gwTakenOut = gwTakenOut;
		this.yardPackages = yardPackages;
		this.cellAreaAllocated = cellAreaAllocated;
		this.areaReleased = areaReleased;
		this.transporterStatus = transporterStatus;
		this.transporter = transporter;
		this.transporterName = transporterName;
		this.vehicleNo = vehicleNo;
		this.driverName = driverName;
		this.comments = comments;
		this.remarks = remarks;
		this.gateOutId = gateOutId;
		this.gateOutDate = gateOutDate;
		this.sl = sl;
		this.destuffLineId = destuffLineId;
		this.destuffId = destuffId;
		this.drt = drt;
		this.grnNo = grnNo;
		this.grnDate = grnDate;
		this.cinNo = cinNo;
		this.cinDate = cinDate;
		this.stampDuty = stampDuty;
		this.splGateOutFlag = splGateOutFlag;
		this.dpdFlag = dpdFlag;
		this.doNo = doNo;
		this.doDate = doDate;
		this.doValidityDate = doValidityDate;
		this.oocNo = oocNo;
		this.oocDate = oocDate;
		this.loadingStartDate = loadingStartDate;
		this.loadingEndDate = loadingEndDate;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.editedBy = editedBy;
		this.editedDate = editedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.yardLocation = yardLocation;
		this.yardBlock = yardBlock;
		this.blockCellNo = blockCellNo;
		this.yardLocation1 = yardLocation1;
		this.yardBlock1 = yardBlock1;
		this.blockCellNo1 = blockCellNo1;
		this.vehStatus = vehStatus;
		this.gatePassType = gatePassType;
		this.othPartyId = othPartyId;
		this.webCamPath = webCamPath;
		this.scannerType = scannerType;
		this.iso = iso;
		this.actualSealNo = actualSealNo;
		this.mtyYardLocation = mtyYardLocation;
		this.gateOutQty = gateOutQty;
	}
	
	
	



	public int getGateOutQty() {
		return gateOutQty;
	}



	public void setGateOutQty(int gateOutQty) {
		this.gateOutQty = gateOutQty;
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

	public String getGatePassId() {
		return gatePassId;
	}

	public void setGatePassId(String gatePassId) {
		this.gatePassId = gatePassId;
	}

	public int getSrNo() {
		return srNo;
	}

	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}

	public String getVehicleGatePassId() {
		return vehicleGatePassId;
	}

	public void setVehicleGatePassId(String vehicleGatePassId) {
		this.vehicleGatePassId = vehicleGatePassId;
	}

	public BigDecimal getConSrNo() {
		return conSrNo;
	}

	public void setConSrNo(BigDecimal conSrNo) {
		this.conSrNo = conSrNo;
	}

	public String getProfitcentreId() {
		return profitcentreId;
	}

	public void setProfitcentreId(String profitcentreId) {
		this.profitcentreId = profitcentreId;
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

	public Date getGatePassDate() {
		return gatePassDate;
	}

	public void setGatePassDate(Date gatePassDate) {
		this.gatePassDate = gatePassDate;
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

	public String getIgmTransId() {
		return igmTransId;
	}

	public void setIgmTransId(String igmTransId) {
		this.igmTransId = igmTransId;
	}

	public String getNocNo() {
		return nocNo;
	}

	public void setNocNo(String nocNo) {
		this.nocNo = nocNo;
	}

	public Date getNocValidityDate() {
		return nocValidityDate;
	}

	public void setNocValidityDate(Date nocValidityDate) {
		this.nocValidityDate = nocValidityDate;
	}

	public String getShift() {
		return shift;
	}

	public void setShift(String shift) {
		this.shift = shift;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
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

	public String getBoe() {
		return boe;
	}

	public void setBoe(String boe) {
		this.boe = boe;
	}

	public Date getBeDate() {
		return beDate;
	}

	public void setBeDate(Date beDate) {
		this.beDate = beDate;
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

	public String getViaNo() {
		return viaNo;
	}

	public void setViaNo(String viaNo) {
		this.viaNo = viaNo;
	}

	public String getCommodity() {
		return commodity;
	}

	public void setCommodity(String commodity) {
		this.commodity = commodity;
	}

	public BigDecimal getGrossWt() {
		return grossWt;
	}

	public void setGrossWt(BigDecimal grossWt) {
		this.grossWt = grossWt;
	}

	public int getNoOfPackage() {
		return noOfPackage;
	}

	public void setNoOfPackage(int noOfPackage) {
		this.noOfPackage = noOfPackage;
	}

	public BigDecimal getQtyTakenOut() {
		return qtyTakenOut;
	}

	public void setQtyTakenOut(BigDecimal qtyTakenOut) {
		this.qtyTakenOut = qtyTakenOut;
	}

	public BigDecimal getVehicleQtyTakenOut() {
		return vehicleQtyTakenOut;
	}

	public void setVehicleQtyTakenOut(BigDecimal vehicleQtyTakenOut) {
		this.vehicleQtyTakenOut = vehicleQtyTakenOut;
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

	public String getTransporterStatus() {
		return transporterStatus;
	}

	public void setTransporterStatus(String transporterStatus) {
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

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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

	public String getSl() {
		return sl;
	}

	public void setSl(String sl) {
		this.sl = sl;
	}

	public String getDestuffLineId() {
		return destuffLineId;
	}

	public void setDestuffLineId(String destuffLineId) {
		this.destuffLineId = destuffLineId;
	}

	public String getDestuffId() {
		return destuffId;
	}

	public void setDestuffId(String destuffId) {
		this.destuffId = destuffId;
	}

	public String getDrt() {
		return drt;
	}

	public void setDrt(String drt) {
		this.drt = drt;
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

	public String getSplGateOutFlag() {
		return splGateOutFlag;
	}

	public void setSplGateOutFlag(String splGateOutFlag) {
		this.splGateOutFlag = splGateOutFlag;
	}

	public String getDpdFlag() {
		return dpdFlag;
	}

	public void setDpdFlag(String dpdFlag) {
		this.dpdFlag = dpdFlag;
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

	public String getVehStatus() {
		return vehStatus;
	}

	public void setVehStatus(String vehStatus) {
		this.vehStatus = vehStatus;
	}

	public String getGatePassType() {
		return gatePassType;
	}

	public void setGatePassType(String gatePassType) {
		this.gatePassType = gatePassType;
	}

	public String getOthPartyId() {
		return othPartyId;
	}

	public void setOthPartyId(String othPartyId) {
		this.othPartyId = othPartyId;
	}

	public String getWebCamPath() {
		return webCamPath;
	}

	public void setWebCamPath(String webCamPath) {
		this.webCamPath = webCamPath;
	}

	public String getScannerType() {
		return scannerType;
	}

	public void setScannerType(String scannerType) {
		this.scannerType = scannerType;
	}

	public String getIso() {
		return iso;
	}

	public void setIso(String iso) {
		this.iso = iso;
	}

	public String getActualSealNo() {
		return actualSealNo;
	}

	public void setActualSealNo(String actualSealNo) {
		this.actualSealNo = actualSealNo;
	}

	public String getMtyYardLocation() {
		return mtyYardLocation;
	}

	public void setMtyYardLocation(String mtyYardLocation) {
		this.mtyYardLocation = mtyYardLocation;
	}



	public ImportGatePass(String gatePassId, int srNo, String profitcentreId, Date gatePassDate, String igmNo,
			String igmLineNo, String igmTransId, String shift, String transType, String importerName,
			String importerAddress1, String importerAddress2, String importerAddress3, String cha, String containerNo,
			String containerSize, String containerType, String blNo, Date blDate, String viaNo, String commodity,
			BigDecimal grossWt, int noOfPackage, BigDecimal qtyTakenOut, BigDecimal vehicleQtyTakenOut,
			BigDecimal gwTakenOut, String vehicleNo, String driverName, String comments, String destuffId, String grnNo,
			Date grnDate, String cinNo, Date cinDate, BigDecimal stampDuty, String doNo, Date doDate,
			Date doValidityDate, String oocNo, Date oocDate, String status, String createdBy, String yardLocation,
			String yardBlock, String blockCellNo, String vehStatus, String mtyYardLocation, int gateOutQty, String boe) {
		super();
		this.gatePassId = gatePassId;
		this.srNo = srNo;
		this.profitcentreId = profitcentreId;
		this.gatePassDate = gatePassDate;
		this.igmNo = igmNo;
		this.igmLineNo = igmLineNo;
		this.igmTransId = igmTransId;
		this.shift = shift;
		this.transType = transType;
		this.importerName = importerName;
		this.importerAddress1 = importerAddress1;
		this.importerAddress2 = importerAddress2;
		this.importerAddress3 = importerAddress3;
		this.cha = cha;
		this.containerNo = containerNo;
		this.containerSize = containerSize;
		this.containerType = containerType;
		this.blNo = blNo;
		this.blDate = blDate;
		this.viaNo = viaNo;
		this.commodity = commodity;
		this.grossWt = grossWt;
		this.noOfPackage = noOfPackage;
		this.qtyTakenOut = qtyTakenOut;
		this.vehicleQtyTakenOut = vehicleQtyTakenOut;
		this.gwTakenOut = gwTakenOut;
		this.vehicleNo = vehicleNo;
		this.driverName = driverName;
		this.comments = comments;
		this.destuffId = destuffId;
		this.grnNo = grnNo;
		this.grnDate = grnDate;
		this.cinNo = cinNo;
		this.cinDate = cinDate;
		this.stampDuty = stampDuty;
		this.doNo = doNo;
		this.doDate = doDate;
		this.doValidityDate = doValidityDate;
		this.oocNo = oocNo;
		this.oocDate = oocDate;
		this.status = status;
		this.createdBy = createdBy;
		this.yardLocation = yardLocation;
		this.yardBlock = yardBlock;
		this.blockCellNo = blockCellNo;
		this.vehStatus = vehStatus;
		this.mtyYardLocation = mtyYardLocation;
		this.gateOutQty = gateOutQty;
		this.boe = boe;
	}



	public ImportGatePass(String gatePassId, int srNo, String profitcentreId, String invoiceNo, String igmNo,
			String igmLineNo, String igmTransId, String nocNo, String transType, String importerName,
			String containerNo) {
		super();
		this.gatePassId = gatePassId;
		this.srNo = srNo;
		this.profitcentreId = profitcentreId;
		this.invoiceNo = invoiceNo;
		this.igmNo = igmNo;
		this.igmLineNo = igmLineNo;
		this.igmTransId = igmTransId;
		this.nocNo = nocNo;
		this.transType = transType;
		this.importerName = importerName;
		this.containerNo = containerNo;
	}
	
	
	
	
	
}
