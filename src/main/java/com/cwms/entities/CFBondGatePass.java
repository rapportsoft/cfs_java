package com.cwms.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "cfbondgatepass")
@IdClass(CfbondGatePassId.class)
public class CFBondGatePass {

    @Id
    @Column(name = "Company_Id", length = 16, nullable = false)
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
    @Column(name = "Sr_No", length = 4, nullable = false)
    private int srNo;

    @Column(name = "Sub_Sr_No", length = 5, nullable = true)
    private String subSrNo;

    @Column(name = "Profitcentre_Id", length = 6, nullable = true)
    private String profitcentreId;

    @Column(name = "Ex_Bonding_Id", length = 10, nullable = true)
    private String exBondingId;

    @Column(name = "In_Bonding_Id", length = 10, nullable = true)
    private String inBondingId;

    @Column(name = "Invoice_No", length = 16, nullable = true)
    private String invoiceNo;

    @Column(name = "Invoice_Date", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
	 @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date invoiceDate;

    @Column(name = "Gate_Pass_Date", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
	 @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date gatePassDate;

    @Column(name = "NOC_No", length = 25, nullable = true)
    private String nocNo;

    @Column(name = "Noc_Trans_Id", length = 10, nullable = true)
    private String nocTransId;

    @Column(name = "Bonding_no", length = 25, nullable = true)
    private String bondingNo;

    @Column(name = "Gate_Out_Id", length = 10, nullable = true)
    private String gateOutId;

    @Column(name = "IGM_Line_No", length = 10, nullable = true)
    private String igmLineNo;

    @Column(name = "Shift", length = 6)
    private String shift;

    @Column(name = "Trans_Type", length = 10, nullable = true)
    private String transType;

    @Column(name = "Importer_Name", length = 60, nullable = true)
    private String importerName;

    @Column(name = "importer_address1", length = 250)
    private String importerAddress1;

    @Column(name = "importer_address2", length = 100)
    private String importerAddress2;

    @Column(name = "importer_address3", length = 100)
    private String importerAddress3;

    @Column(name = "CHA", length = 6, nullable = true)
    private String cha;

    @Column(name = "Container_No", length = 11, nullable = true)
    private String containerNo;

    @Column(name = "BOE", length = 10, nullable = true)
    private String boe;

    @Column(name = "BL_No", length = 10, nullable = true)
    private String blNo;

    @Column(name = "BL_Date", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
	 @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date blDate;

    @Column(name = "BOE_No", length = 15, nullable = true)
    private String boeNo;

    @Column(name = "BOE_Date", nullable = true)
    @Temporal(TemporalType.DATE)
	 @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date boeDate;

    @Column(name = "Commodity", length = 250, nullable = true)
    private String commodity;

    @Column(name = "Gross_Wt", precision = 15, scale = 3, nullable = true)
    private BigDecimal grossWt;

    @Column(name = "No_Of_Package", precision = 8, scale = 0, nullable = true)
    private BigDecimal noOfPackage;

    @Column(name = "In_bond_Packages", precision = 8, scale = 0, nullable = true)
    private BigDecimal inBondPackages;

    @Column(name = "Transporter_Status", length = 1, nullable = true)
    private String transporterStatus;

    @Column(name = "Transporter", length = 6, nullable = true)
    private String transporter;

    @Column(name = "Transporter_Name", length = 50, nullable = true)
    private String transporterName;

    @Column(name = "Vehicle_No", length = 30, nullable = true)
    private String vehicleNo;

    @Column(name = "Driver_Name", length = 50, nullable = true)
    private String driverName;

    @Column(name = "Comments", length = 150, nullable = true)
    private String comments;

    @Column(name = "Block_Cell_No", length = 10)
    private String blockCellNo;

    @Column(name = "SL", length = 10)
    private String sl;

    @Column(name = "No_of_packages", precision = 8, scale = 0)
    private BigDecimal noOfPackages;

    @Column(name = "Qty_Taken_Out", precision = 8, scale = 0, nullable = true)
    private BigDecimal qtyTakenOut;

    @Column(name = "Area_Allocated", precision = 16, scale = 3)
    private BigDecimal areaAllocated;

    @Column(name = "Area_Released", precision = 16, scale = 3)
    private BigDecimal areaReleased;

    @Column(name = "Examiner", length = 35)
    private String examiner;

    @Column(name = "Status", length = 1, nullable = true)
    private String status;

    @Column(name = "Created_By", length = 10, nullable = true)
    private String createdBy;

    @Column(name = "Created_Date", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
	 @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date createdDate;

    @Column(name = "Edited_By", length = 10, nullable = true)
    private String editedBy;

    @Column(name = "Edited_Date", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
	 @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date editedDate;

    @Column(name = "Approved_By", length = 10, nullable = true)
    private String approvedBy;

    @Column(name = "Approved_Date", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
	 @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date approvedDate;

    @Column(name = "Yard_Location", length = 10, nullable = true)
    private String yardLocation;

    @Column(name = "Yard_Block", length = 10, nullable = true)
    private String yardBlock;

    @Column(name = "Contact_No", length = 15, nullable = true)
    private String contactNo;

    @Column(name = "Licence_No", length = 20, nullable = true)
    private String licenceNo;

    @Column(name = "BAL_QTY_OUT", precision = 8, scale = 0, nullable = true)
    private BigDecimal balQtyOut;

    @Column(name = "TOTAL_GROSS_WT", precision = 16, scale = 3, nullable = true)
    private BigDecimal totalGrossWt;

    @Column(name = "Tare_Weight", precision = 15, scale = 3, nullable = true)
    private BigDecimal tareWeight;

    @Column(name = "Net_Weight", precision = 15, scale = 3, nullable = true)
    private BigDecimal netWeight;

    @Column(name = "Delivery_Person_Name", length = 60, nullable = true)
    private String deliveryPersonName;

    @Column(name = "Delivery_Person_Addrs", length = 250, nullable = true)
    private String deliveryPersonAddrs;

    @Column(name = "Veh_Gate_In_Id", length = 10, nullable = true)
    private String vehGateInId;
    
    
    @Column(name = "ExBond_BE_No", length = 20)
    private String exBondBeNo;

    
	@Column(name = "Ex_Bonded_Packages", nullable = true, precision = 6, scale = 0)
	private BigDecimal exBondedPackages = BigDecimal.ZERO;
	
	@Column(name = "Commodity_Description", length = 250)
	private String commodityDescription;
	
	 @Column(name = "Delivery_Order_No", length = 25)
	    private String deliveryOrderNo;

	    @Column(name = "Delivery_Order_Date")
	    @Temporal(TemporalType.TIMESTAMP)
		@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	    private Date deliveryOrderDate;

	    @Column(name = "DO_Validity_Date")
	    @Temporal(TemporalType.TIMESTAMP)
		@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	    private Date doValidityDate;

	
	    
	    @Column(name = "Yard_Qty_Taken_Out", precision = 8, scale = 0, nullable = true)
	    private BigDecimal yardQtyTakenOut;
	    
	    
	    @Column(name = "Yard_Area_Released", precision = 16, scale = 3)
	    private BigDecimal yardAreaReleased;
	    
	    
	    
	public BigDecimal getYardQtyTakenOut() {
			return yardQtyTakenOut;
		}

		public void setYardQtyTakenOut(BigDecimal yardQtyTakenOut) {
			this.yardQtyTakenOut = yardQtyTakenOut;
		}

		public BigDecimal getYardAreaReleased() {
			return yardAreaReleased;
		}

		public void setYardAreaReleased(BigDecimal yardAreaReleased) {
			this.yardAreaReleased = yardAreaReleased;
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

	public BigDecimal getExBondedPackages() {
		return exBondedPackages;
	}

	public void setExBondedPackages(BigDecimal exBondedPackages) {
		this.exBondedPackages = exBondedPackages;
	}

	public String getExBondBeNo() {
		return exBondBeNo;
	}

	public void setExBondBeNo(String exBondBeNo) {
		this.exBondBeNo = exBondBeNo;
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

	public String getSubSrNo() {
		return subSrNo;
	}

	public void setSubSrNo(String subSrNo) {
		this.subSrNo = subSrNo;
	}

	public String getProfitcentreId() {
		return profitcentreId;
	}

	public void setProfitcentreId(String profitcentreId) {
		this.profitcentreId = profitcentreId;
	}

	public String getExBondingId() {
		return exBondingId;
	}

	public void setExBondingId(String exBondingId) {
		this.exBondingId = exBondingId;
	}

	public String getInBondingId() {
		return inBondingId;
	}

	public void setInBondingId(String inBondingId) {
		this.inBondingId = inBondingId;
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

	public String getNocNo() {
		return nocNo;
	}

	public void setNocNo(String nocNo) {
		this.nocNo = nocNo;
	}

	public String getNocTransId() {
		return nocTransId;
	}

	public void setNocTransId(String nocTransId) {
		this.nocTransId = nocTransId;
	}

	public String getBondingNo() {
		return bondingNo;
	}

	public void setBondingNo(String bondingNo) {
		this.bondingNo = bondingNo;
	}

	public String getGateOutId() {
		return gateOutId;
	}

	public void setGateOutId(String gateOutId) {
		this.gateOutId = gateOutId;
	}

	public String getIgmLineNo() {
		return igmLineNo;
	}

	public void setIgmLineNo(String igmLineNo) {
		this.igmLineNo = igmLineNo;
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

	public String getBoe() {
		return boe;
	}

	public void setBoe(String boe) {
		this.boe = boe;
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

	public BigDecimal getNoOfPackage() {
		return noOfPackage;
	}

	public void setNoOfPackage(BigDecimal noOfPackage) {
		this.noOfPackage = noOfPackage;
	}

	public BigDecimal getInBondPackages() {
		return inBondPackages;
	}

	public void setInBondPackages(BigDecimal inBondPackages) {
		this.inBondPackages = inBondPackages;
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

	public String getBlockCellNo() {
		return blockCellNo;
	}

	public void setBlockCellNo(String blockCellNo) {
		this.blockCellNo = blockCellNo;
	}

	public String getSl() {
		return sl;
	}

	public void setSl(String sl) {
		this.sl = sl;
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

	public BigDecimal getAreaAllocated() {
		return areaAllocated;
	}

	public void setAreaAllocated(BigDecimal areaAllocated) {
		this.areaAllocated = areaAllocated;
	}

	public BigDecimal getAreaReleased() {
		return areaReleased;
	}

	public void setAreaReleased(BigDecimal areaReleased) {
		this.areaReleased = areaReleased;
	}

	public String getExaminer() {
		return examiner;
	}

	public void setExaminer(String examiner) {
		this.examiner = examiner;
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

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getLicenceNo() {
		return licenceNo;
	}

	public void setLicenceNo(String licenceNo) {
		this.licenceNo = licenceNo;
	}

	public BigDecimal getBalQtyOut() {
		return balQtyOut;
	}

	public void setBalQtyOut(BigDecimal balQtyOut) {
		this.balQtyOut = balQtyOut;
	}

	public BigDecimal getTotalGrossWt() {
		return totalGrossWt;
	}

	public void setTotalGrossWt(BigDecimal totalGrossWt) {
		this.totalGrossWt = totalGrossWt;
	}

	public BigDecimal getTareWeight() {
		return tareWeight;
	}

	public void setTareWeight(BigDecimal tareWeight) {
		this.tareWeight = tareWeight;
	}

	public BigDecimal getNetWeight() {
		return netWeight;
	}

	public void setNetWeight(BigDecimal netWeight) {
		this.netWeight = netWeight;
	}

	public String getDeliveryPersonName() {
		return deliveryPersonName;
	}

	public void setDeliveryPersonName(String deliveryPersonName) {
		this.deliveryPersonName = deliveryPersonName;
	}

	public String getDeliveryPersonAddrs() {
		return deliveryPersonAddrs;
	}

	public void setDeliveryPersonAddrs(String deliveryPersonAddrs) {
		this.deliveryPersonAddrs = deliveryPersonAddrs;
	}

	public String getVehGateInId() {
		return vehGateInId;
	}

	public void setVehGateInId(String vehGateInId) {
		this.vehGateInId = vehGateInId;
	}

	
	public String getCommodityDescription() {
		return commodityDescription;
	}

	public void setCommodityDescription(String commodityDescription) {
		this.commodityDescription = commodityDescription;
	}

	public CFBondGatePass() {
		super();
		// TODO Auto-generated constructor stub
	}

	


	public CFBondGatePass(String companyId, String branchId, String finYear, String gatePassId, int srNo,
			String subSrNo, String profitcentreId, String exBondingId, String inBondingId, String invoiceNo,
			Date invoiceDate, Date gatePassDate, String nocNo, String nocTransId, String bondingNo, String gateOutId,
			String igmLineNo, String shift, String transType, String importerName, String importerAddress1,
			String importerAddress2, String importerAddress3, String cha, String containerNo, String boe, String blNo,
			Date blDate, String boeNo, Date boeDate, String commodity, BigDecimal grossWt, BigDecimal noOfPackage,
			BigDecimal inBondPackages, String transporterStatus, String transporter, String transporterName,
			String vehicleNo, String driverName, String comments, String blockCellNo, String sl,
			BigDecimal noOfPackages, BigDecimal qtyTakenOut, BigDecimal areaAllocated, BigDecimal areaReleased,
			String examiner, String status, String createdBy, Date createdDate, String editedBy, Date editedDate,
			String approvedBy, Date approvedDate, String yardLocation, String yardBlock, String contactNo,
			String licenceNo, BigDecimal balQtyOut, BigDecimal totalGrossWt, BigDecimal tareWeight,
			BigDecimal netWeight, String deliveryPersonName, String deliveryPersonAddrs, String vehGateInId,
			String exBondBeNo, BigDecimal exBondedPackages, String commodityDescription, String deliveryOrderNo,
			Date deliveryOrderDate, Date doValidityDate, BigDecimal yardQtyTakenOut, BigDecimal yardAreaReleased) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.finYear = finYear;
		this.gatePassId = gatePassId;
		this.srNo = srNo;
		this.subSrNo = subSrNo;
		this.profitcentreId = profitcentreId;
		this.exBondingId = exBondingId;
		this.inBondingId = inBondingId;
		this.invoiceNo = invoiceNo;
		this.invoiceDate = invoiceDate;
		this.gatePassDate = gatePassDate;
		this.nocNo = nocNo;
		this.nocTransId = nocTransId;
		this.bondingNo = bondingNo;
		this.gateOutId = gateOutId;
		this.igmLineNo = igmLineNo;
		this.shift = shift;
		this.transType = transType;
		this.importerName = importerName;
		this.importerAddress1 = importerAddress1;
		this.importerAddress2 = importerAddress2;
		this.importerAddress3 = importerAddress3;
		this.cha = cha;
		this.containerNo = containerNo;
		this.boe = boe;
		this.blNo = blNo;
		this.blDate = blDate;
		this.boeNo = boeNo;
		this.boeDate = boeDate;
		this.commodity = commodity;
		this.grossWt = grossWt;
		this.noOfPackage = noOfPackage;
		this.inBondPackages = inBondPackages;
		this.transporterStatus = transporterStatus;
		this.transporter = transporter;
		this.transporterName = transporterName;
		this.vehicleNo = vehicleNo;
		this.driverName = driverName;
		this.comments = comments;
		this.blockCellNo = blockCellNo;
		this.sl = sl;
		this.noOfPackages = noOfPackages;
		this.qtyTakenOut = qtyTakenOut;
		this.areaAllocated = areaAllocated;
		this.areaReleased = areaReleased;
		this.examiner = examiner;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.editedBy = editedBy;
		this.editedDate = editedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.yardLocation = yardLocation;
		this.yardBlock = yardBlock;
		this.contactNo = contactNo;
		this.licenceNo = licenceNo;
		this.balQtyOut = balQtyOut;
		this.totalGrossWt = totalGrossWt;
		this.tareWeight = tareWeight;
		this.netWeight = netWeight;
		this.deliveryPersonName = deliveryPersonName;
		this.deliveryPersonAddrs = deliveryPersonAddrs;
		this.vehGateInId = vehGateInId;
		this.exBondBeNo = exBondBeNo;
		this.exBondedPackages = exBondedPackages;
		this.commodityDescription = commodityDescription;
		this.deliveryOrderNo = deliveryOrderNo;
		this.deliveryOrderDate = deliveryOrderDate;
		this.doValidityDate = doValidityDate;
		this.yardQtyTakenOut = yardQtyTakenOut;
		this.yardAreaReleased = yardAreaReleased;
	}

	@Override
	public String toString() {
		return "CFBondGatePass [companyId=" + companyId + ", branchId=" + branchId + ", finYear=" + finYear
				+ ", gatePassId=" + gatePassId + ", srNo=" + srNo + ", subSrNo=" + subSrNo + ", profitcentreId="
				+ profitcentreId + ", exBondingId=" + exBondingId + ", inBondingId=" + inBondingId + ", invoiceNo="
				+ invoiceNo + ", invoiceDate=" + invoiceDate + ", gatePassDate=" + gatePassDate + ", nocNo=" + nocNo
				+ ", nocTransId=" + nocTransId + ", bondingNo=" + bondingNo + ", gateOutId=" + gateOutId
				+ ", igmLineNo=" + igmLineNo + ", shift=" + shift + ", transType=" + transType + ", importerName="
				+ importerName + ", importerAddress1=" + importerAddress1 + ", importerAddress2=" + importerAddress2
				+ ", importerAddress3=" + importerAddress3 + ", cha=" + cha + ", containerNo=" + containerNo + ", boe="
				+ boe + ", blNo=" + blNo + ", blDate=" + blDate + ", boeNo=" + boeNo + ", boeDate=" + boeDate
				+ ", commodity=" + commodity + ", grossWt=" + grossWt + ", noOfPackage=" + noOfPackage
				+ ", inBondPackages=" + inBondPackages + ", transporterStatus=" + transporterStatus + ", transporter="
				+ transporter + ", transporterName=" + transporterName + ", vehicleNo=" + vehicleNo + ", driverName="
				+ driverName + ", comments=" + comments + ", blockCellNo=" + blockCellNo + ", sl=" + sl
				+ ", noOfPackages=" + noOfPackages + ", qtyTakenOut=" + qtyTakenOut + ", areaAllocated=" + areaAllocated
				+ ", areaReleased=" + areaReleased + ", examiner=" + examiner + ", status=" + status + ", createdBy="
				+ createdBy + ", createdDate=" + createdDate + ", editedBy=" + editedBy + ", editedDate=" + editedDate
				+ ", approvedBy=" + approvedBy + ", approvedDate=" + approvedDate + ", yardLocation=" + yardLocation
				+ ", yardBlock=" + yardBlock + ", contactNo=" + contactNo + ", licenceNo=" + licenceNo + ", balQtyOut="
				+ balQtyOut + ", totalGrossWt=" + totalGrossWt + ", tareWeight=" + tareWeight + ", netWeight="
				+ netWeight + ", deliveryPersonName=" + deliveryPersonName + ", deliveryPersonAddrs="
				+ deliveryPersonAddrs + ", vehGateInId=" + vehGateInId + ", exBondBeNo=" + exBondBeNo
				+ ", exBondedPackages=" + exBondedPackages + ", commodityDescription=" + commodityDescription
				+ ", deliveryOrderNo=" + deliveryOrderNo + ", deliveryOrderDate=" + deliveryOrderDate
				+ ", doValidityDate=" + doValidityDate + ", yardQtyTakenOut=" + yardQtyTakenOut + ", yardAreaReleased="
				+ yardAreaReleased + "]";
	}

	public CFBondGatePass(String companyId, String branchId, String gatePassId, int srNo, String subSrNo,
			String profitcentreId, String exBondingId, String inBondingId, String invoiceNo, Date invoiceDate,
			Date gatePassDate, String nocNo, String nocTransId, String bondingNo, String gateOutId, String igmLineNo,
			String shift, String transType, String importerName, String importerAddress1, String importerAddress2,
			String importerAddress3, String cha, String containerNo, String boe, String boeNo, Date boeDate,
			String commodity, BigDecimal grossWt, BigDecimal noOfPackage, BigDecimal inBondPackages,
			String transporterStatus, String transporter, String transporterName, String vehicleNo, String driverName,
			String comments, String blockCellNo, String sl, BigDecimal noOfPackages, BigDecimal qtyTakenOut,
			BigDecimal areaAllocated, BigDecimal areaReleased, String examiner, String status, String yardLocation,
			String yardBlock, String contactNo, String licenceNo, BigDecimal balQtyOut, BigDecimal totalGrossWt,
			BigDecimal tareWeight, BigDecimal netWeight, String deliveryPersonName, String deliveryPersonAddrs,
			String vehGateInId, String exBondBeNo, BigDecimal exBondedPackages, String commodityDescription,
			String createdBy,String editedBy,
			String approvedBy,String deliveryOrderNo,
			Date deliveryOrderDate, Date doValidityDate,BigDecimal yardQtyTakenOut, BigDecimal yardAreaReleased) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.gatePassId = gatePassId;
		this.srNo = srNo;
		this.subSrNo = subSrNo;
		this.profitcentreId = profitcentreId;
		this.exBondingId = exBondingId;
		this.inBondingId = inBondingId;
		this.invoiceNo = invoiceNo;
		this.invoiceDate = invoiceDate;
		this.gatePassDate = gatePassDate;
		this.nocNo = nocNo;
		this.nocTransId = nocTransId;
		this.bondingNo = bondingNo;
		this.gateOutId = gateOutId;
		this.igmLineNo = igmLineNo;
		this.shift = shift;
		this.transType = transType;
		this.importerName = importerName;
		this.importerAddress1 = importerAddress1;
		this.importerAddress2 = importerAddress2;
		this.importerAddress3 = importerAddress3;
		this.cha = cha;
		this.containerNo = containerNo;
		this.boe = boe;
		this.boeNo = boeNo;
		this.boeDate = boeDate;
		this.commodity = commodity;
		this.grossWt = grossWt;
		this.noOfPackage = noOfPackage;
		this.inBondPackages = inBondPackages;
		this.transporterStatus = transporterStatus;
		this.transporter = transporter;
		this.transporterName = transporterName;
		this.vehicleNo = vehicleNo;
		this.driverName = driverName;
		this.comments = comments;
		this.blockCellNo = blockCellNo;
		this.sl = sl;
		this.noOfPackages = noOfPackages;
		this.qtyTakenOut = qtyTakenOut;
		this.areaAllocated = areaAllocated;
		this.areaReleased = areaReleased;
		this.examiner = examiner;
		this.status = status;
		this.yardLocation = yardLocation;
		this.yardBlock = yardBlock;
		this.contactNo = contactNo;
		this.licenceNo = licenceNo;
		this.balQtyOut = balQtyOut;
		this.totalGrossWt = totalGrossWt;
		this.tareWeight = tareWeight;
		this.netWeight = netWeight;
		this.deliveryPersonName = deliveryPersonName;
		this.deliveryPersonAddrs = deliveryPersonAddrs;
		this.vehGateInId = vehGateInId;
		this.exBondBeNo = exBondBeNo;
		this.exBondedPackages = exBondedPackages;
		this.commodityDescription = commodityDescription;
		this.createdBy = createdBy;
		this.editedBy = editedBy;
		this.approvedBy = approvedBy;
		this.deliveryOrderNo = deliveryOrderNo;
		this.deliveryOrderDate = deliveryOrderDate;
		this.doValidityDate = doValidityDate;
		this.yardQtyTakenOut = yardQtyTakenOut;
		this.yardAreaReleased = yardAreaReleased;
	}

	
	
	
	
	// for cfbonding main search 
	public CFBondGatePass(String companyId, String branchId, String finYear, String gatePassId, String exBondingId,
			String inBondingId, String nocNo, String nocTransId, String bondingNo,String exBondBeNo) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.finYear = finYear;
		this.gatePassId = gatePassId;
		this.exBondingId = exBondingId;
		this.inBondingId = inBondingId;
		this.nocNo = nocNo;
		this.nocTransId = nocTransId;
		this.bondingNo = bondingNo;
		this.exBondBeNo = exBondBeNo;
	}


	
	public CFBondGatePass(String companyId, String branchId, String finYear, String gatePassId, int srNo,
			String nocTransId, String nocNo,String bondingNo, String igmLineNo, BigDecimal noOfPackage, BigDecimal noOfPackages,
			BigDecimal qtyTakenOut, String exBondBeNo, BigDecimal exBondedPackages, String commodityDescription,String commodity,BigDecimal grossWt,String approvedBy,String deliveryOrderNo,
			Date deliveryOrderDate, Date doValidityDate) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.finYear = finYear;
		this.gatePassId = gatePassId;
		this.srNo = srNo;
		this.nocTransId = nocTransId;
		this.nocNo = nocNo;
		this.bondingNo = bondingNo;
		this.igmLineNo = igmLineNo;
		this.noOfPackage = noOfPackage;
		this.noOfPackages = noOfPackages;
		this.qtyTakenOut = qtyTakenOut;
		this.exBondBeNo = exBondBeNo;
		this.exBondedPackages = exBondedPackages;
		this.commodityDescription = commodityDescription;
		this.commodity=commodity;
		this.grossWt=grossWt;
		this.approvedBy=approvedBy;
		this.deliveryOrderNo = deliveryOrderNo;
		this.deliveryOrderDate = deliveryOrderDate;
		this.doValidityDate = doValidityDate;
	}

	

}
