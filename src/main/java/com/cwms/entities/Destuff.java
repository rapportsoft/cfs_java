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
@IdClass(DestuffId.class)
@Table(name="cfdestuffcn")
public class Destuff {
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
	    @Column(name = "DeStuff_Id", length = 10)
	    private String deStuffId;

	    @Column(name = "DeStuff_Date")
	    @Temporal(TemporalType.TIMESTAMP)
	    private Date deStuffDate;

	    @Column(name = "Profitcentre_Id", length = 6)
	    private String profitcentreId;

	    @Column(name = "IGM_Trans_Id", length = 10)
	    private String igmTransId;

	    @Column(name = "IGM_No", length = 10)
	    private String igmNo;

	    @Column(name = "IGM_Line_No", length = 7)
	    private String igmLineNo;

	    @Column(name = "IGM_Date")
	    @Temporal(TemporalType.DATE)
	    private Date igmDate;

	    @Column(name = "Trans_type", length = 5)
	    private String transType;

	    @Column(name = "DRT", length = 1)
	    private String drt;

	    @Column(name = "VIA_NO", length = 10)
	    private String viaNo;

	    @Column(name = "Shipping_Agent", length = 6)
	    private String shippingAgent;

	    @Column(name = "Shipping_Line", length = 6)
	    private String shippingLine;

	    @Column(name = "Container_No", length = 11)
	    private String containerNo;

	    @Column(name = "Container_Type", length = 6)
	    private String containerType;

	    @Column(name = "Container_Size", length = 6)
	    private String containerSize;

	    @Column(name = "Container_Status", length = 3)
	    private String containerStatus;

	    @Column(name = "Haz", length = 1)
	    private String haz;

	    @Column(name = "Periodic_Bill", length = 1)
	    private String periodicBill;

	    @Column(name = "Gross_Weight", precision = 15, scale = 3)
	    private BigDecimal grossWeight;

	    @Column(name = "Container_Seal_No", length = 15)
	    private String containerSealNo;

	    @Column(name = "Custom_Seal_No", length = 15)
	    private String customSealNo;

	    @Column(name = "SA_Seal_No", length = 15)
	    private String saSealNo;

	    @Column(name = "On_Account_Of", length = 6)
	    private String onAccountOf;

	    @Column(name = "Gate_In_Id", length = 10)
	    private String gateInId;

	    @Column(name = "Gate_In_Date")
	    @Temporal(TemporalType.TIMESTAMP)
	    private Date gateInDate;

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

	    @Column(name = "Area_Occupied", precision = 8, scale = 3)
	    private BigDecimal areaOccupied;

	    @Column(name = "Yard_Packages", precision = 8, scale = 3)
	    private BigDecimal yardPackages;

	    @Column(name = "POD", length = 6)
	    private String pod;

	    @Column(name = "Invoice_Type", length = 3)
	    private String invoiceType;

	    @Column(name = "Gate_In_Type", length = 6)
	    private String gateInType;

	    @Column(name = "SHIFT", length = 6)
	    private String shift;

	    @Column(name = "BL_GAIN_LOSS_ID", length = 10)
	    private String blGainLossId;

	    @Column(name = "MTY_Status", length = 1)
	    private String mtyStatus;

	    @Column(name = "MTY_Date")
	    @Temporal(TemporalType.TIMESTAMP)
	    private Date mtyDate;

	    @Column(name = "DO_Entry_Flag", length = 1)
	    private String doEntryFlag;

	    @Column(name = "DO_Entry_date")
	    @Temporal(TemporalType.TIMESTAMP)
	    private Date doEntryDate;

	    @Column(name = "Status", length = 1)
	    private String status;

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

	    @Column(name = "Destuff_Type", length = 20)
	    private String destuffType;

	    @Column(name = "DestuffFrom_Date")
	    @Temporal(TemporalType.TIMESTAMP)
	    private Date destuffFromDate;

	    @Column(name = "DestuffTo_Date")
	    @Temporal(TemporalType.TIMESTAMP)
	    private Date destuffToDate;
	    
	    @Column(name="Work_Order_No",length = 20)
	    private String workOrderNo;

		public Destuff() {
			super();
			// TODO Auto-generated constructor stub
		}

		
		public Destuff(String companyId, String branchId, String finYear, String deStuffId, Date deStuffDate,
				String profitcentreId, String igmTransId, String igmNo, String igmLineNo, Date igmDate,
				String transType, String drt, String viaNo, String shippingAgent, String shippingLine,
				String containerNo, String containerType, String containerSize, String containerStatus, String haz,
				String periodicBill, BigDecimal grossWeight, String containerSealNo, String customSealNo,
				String saSealNo, String onAccountOf, String gateInId, Date gateInDate, String yardLocation,
				String yardBlock, String blockCellNo, String yardLocation1, String yardBlock1, String blockCellNo1,
				BigDecimal areaOccupied, BigDecimal yardPackages, String pod, String invoiceType, String gateInType,
				String shift, String blGainLossId, String mtyStatus, Date mtyDate, String doEntryFlag, Date doEntryDate,
				String status, String createdBy, Date createdDate, String editedBy, Date editedDate, String approvedBy,
				Date approvedDate, String destuffType, Date destuffFromDate, Date destuffToDate, String workOrderNo) {
			super();
			this.companyId = companyId;
			this.branchId = branchId;
			this.finYear = finYear;
			this.deStuffId = deStuffId;
			this.deStuffDate = deStuffDate;
			this.profitcentreId = profitcentreId;
			this.igmTransId = igmTransId;
			this.igmNo = igmNo;
			this.igmLineNo = igmLineNo;
			this.igmDate = igmDate;
			this.transType = transType;
			this.drt = drt;
			this.viaNo = viaNo;
			this.shippingAgent = shippingAgent;
			this.shippingLine = shippingLine;
			this.containerNo = containerNo;
			this.containerType = containerType;
			this.containerSize = containerSize;
			this.containerStatus = containerStatus;
			this.haz = haz;
			this.periodicBill = periodicBill;
			this.grossWeight = grossWeight;
			this.containerSealNo = containerSealNo;
			this.customSealNo = customSealNo;
			this.saSealNo = saSealNo;
			this.onAccountOf = onAccountOf;
			this.gateInId = gateInId;
			this.gateInDate = gateInDate;
			this.yardLocation = yardLocation;
			this.yardBlock = yardBlock;
			this.blockCellNo = blockCellNo;
			this.yardLocation1 = yardLocation1;
			this.yardBlock1 = yardBlock1;
			this.blockCellNo1 = blockCellNo1;
			this.areaOccupied = areaOccupied;
			this.yardPackages = yardPackages;
			this.pod = pod;
			this.invoiceType = invoiceType;
			this.gateInType = gateInType;
			this.shift = shift;
			this.blGainLossId = blGainLossId;
			this.mtyStatus = mtyStatus;
			this.mtyDate = mtyDate;
			this.doEntryFlag = doEntryFlag;
			this.doEntryDate = doEntryDate;
			this.status = status;
			this.createdBy = createdBy;
			this.createdDate = createdDate;
			this.editedBy = editedBy;
			this.editedDate = editedDate;
			this.approvedBy = approvedBy;
			this.approvedDate = approvedDate;
			this.destuffType = destuffType;
			this.destuffFromDate = destuffFromDate;
			this.destuffToDate = destuffToDate;
			this.workOrderNo = workOrderNo;
		}
		
		


		public String getWorkOrderNo() {
			return workOrderNo;
		}


		public void setWorkOrderNo(String workOrderNo) {
			this.workOrderNo = workOrderNo;
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

		public String getDeStuffId() {
			return deStuffId;
		}

		public void setDeStuffId(String deStuffId) {
			this.deStuffId = deStuffId;
		}

		public Date getDeStuffDate() {
			return deStuffDate;
		}

		public void setDeStuffDate(Date deStuffDate) {
			this.deStuffDate = deStuffDate;
		}

		public String getProfitcentreId() {
			return profitcentreId;
		}

		public void setProfitcentreId(String profitcentreId) {
			this.profitcentreId = profitcentreId;
		}

		public String getIgmTransId() {
			return igmTransId;
		}

		public void setIgmTransId(String igmTransId) {
			this.igmTransId = igmTransId;
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

		public Date getIgmDate() {
			return igmDate;
		}

		public void setIgmDate(Date igmDate) {
			this.igmDate = igmDate;
		}

		public String getTransType() {
			return transType;
		}

		public void setTransType(String transType) {
			this.transType = transType;
		}

		public String getDrt() {
			return drt;
		}

		public void setDrt(String drt) {
			this.drt = drt;
		}

		public String getViaNo() {
			return viaNo;
		}

		public void setViaNo(String viaNo) {
			this.viaNo = viaNo;
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

		public String getContainerNo() {
			return containerNo;
		}

		public void setContainerNo(String containerNo) {
			this.containerNo = containerNo;
		}

		public String getContainerType() {
			return containerType;
		}

		public void setContainerType(String containerType) {
			this.containerType = containerType;
		}

		public String getContainerSize() {
			return containerSize;
		}

		public void setContainerSize(String containerSize) {
			this.containerSize = containerSize;
		}

		public String getContainerStatus() {
			return containerStatus;
		}

		public void setContainerStatus(String containerStatus) {
			this.containerStatus = containerStatus;
		}

		public String getHaz() {
			return haz;
		}

		public void setHaz(String haz) {
			this.haz = haz;
		}

		public String getPeriodicBill() {
			return periodicBill;
		}

		public void setPeriodicBill(String periodicBill) {
			this.periodicBill = periodicBill;
		}

		public BigDecimal getGrossWeight() {
			return grossWeight;
		}

		public void setGrossWeight(BigDecimal grossWeight) {
			this.grossWeight = grossWeight;
		}

		public String getContainerSealNo() {
			return containerSealNo;
		}

		public void setContainerSealNo(String containerSealNo) {
			this.containerSealNo = containerSealNo;
		}

		public String getCustomSealNo() {
			return customSealNo;
		}

		public void setCustomSealNo(String customSealNo) {
			this.customSealNo = customSealNo;
		}

		public String getSaSealNo() {
			return saSealNo;
		}

		public void setSaSealNo(String saSealNo) {
			this.saSealNo = saSealNo;
		}

		public String getOnAccountOf() {
			return onAccountOf;
		}

		public void setOnAccountOf(String onAccountOf) {
			this.onAccountOf = onAccountOf;
		}

		public String getGateInId() {
			return gateInId;
		}

		public void setGateInId(String gateInId) {
			this.gateInId = gateInId;
		}

		public Date getGateInDate() {
			return gateInDate;
		}

		public void setGateInDate(Date gateInDate) {
			this.gateInDate = gateInDate;
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

		public BigDecimal getAreaOccupied() {
			return areaOccupied;
		}

		public void setAreaOccupied(BigDecimal areaOccupied) {
			this.areaOccupied = areaOccupied;
		}

		public BigDecimal getYardPackages() {
			return yardPackages;
		}

		public void setYardPackages(BigDecimal yardPackages) {
			this.yardPackages = yardPackages;
		}

		public String getPod() {
			return pod;
		}

		public void setPod(String pod) {
			this.pod = pod;
		}

		public String getInvoiceType() {
			return invoiceType;
		}

		public void setInvoiceType(String invoiceType) {
			this.invoiceType = invoiceType;
		}

		public String getGateInType() {
			return gateInType;
		}

		public void setGateInType(String gateInType) {
			this.gateInType = gateInType;
		}

		public String getShift() {
			return shift;
		}

		public void setShift(String shift) {
			this.shift = shift;
		}

		public String getBlGainLossId() {
			return blGainLossId;
		}

		public void setBlGainLossId(String blGainLossId) {
			this.blGainLossId = blGainLossId;
		}

		public String getMtyStatus() {
			return mtyStatus;
		}

		public void setMtyStatus(String mtyStatus) {
			this.mtyStatus = mtyStatus;
		}

		public Date getMtyDate() {
			return mtyDate;
		}

		public void setMtyDate(Date mtyDate) {
			this.mtyDate = mtyDate;
		}

		public String getDoEntryFlag() {
			return doEntryFlag;
		}

		public void setDoEntryFlag(String doEntryFlag) {
			this.doEntryFlag = doEntryFlag;
		}

		public Date getDoEntryDate() {
			return doEntryDate;
		}

		public void setDoEntryDate(Date doEntryDate) {
			this.doEntryDate = doEntryDate;
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

		public String getDestuffType() {
			return destuffType;
		}

		public void setDestuffType(String destuffType) {
			this.destuffType = destuffType;
		}

		public Date getDestuffFromDate() {
			return destuffFromDate;
		}

		public void setDestuffFromDate(Date destuffFromDate) {
			this.destuffFromDate = destuffFromDate;
		}

		public Date getDestuffToDate() {
			return destuffToDate;
		}

		public void setDestuffToDate(Date destuffToDate) {
			this.destuffToDate = destuffToDate;
		}


		public Destuff(String deStuffId, Date deStuffDate, String profitcentreId, String igmTransId, String igmNo,
				String igmLineNo, Date igmDate, String transType, String drt, String viaNo, String shippingAgent,
				String shippingLine, String containerNo, String containerType, String containerSize,
				String containerStatus, String haz, BigDecimal grossWeight, String containerSealNo, String customSealNo,
				String onAccountOf, String gateInId, Date gateInDate, String yardLocation, String yardBlock,
				String blockCellNo, String yardLocation1, String yardBlock1, String blockCellNo1,
				BigDecimal areaOccupied, BigDecimal yardPackages, String pod, String gateInType, String shift,
				String status, String createdBy, String approvedBy, String destuffType, Date destuffFromDate,
				Date destuffToDate, String workOrderNo) {
			super();
			this.deStuffId = deStuffId;
			this.deStuffDate = deStuffDate;
			this.profitcentreId = profitcentreId;
			this.igmTransId = igmTransId;
			this.igmNo = igmNo;
			this.igmLineNo = igmLineNo;
			this.igmDate = igmDate;
			this.transType = transType;
			this.drt = drt;
			this.viaNo = viaNo;
			this.shippingAgent = shippingAgent;
			this.shippingLine = shippingLine;
			this.containerNo = containerNo;
			this.containerType = containerType;
			this.containerSize = containerSize;
			this.containerStatus = containerStatus;
			this.haz = haz;
			this.grossWeight = grossWeight;
			this.containerSealNo = containerSealNo;
			this.customSealNo = customSealNo;
			this.onAccountOf = onAccountOf;
			this.gateInId = gateInId;
			this.gateInDate = gateInDate;
			this.yardLocation = yardLocation;
			this.yardBlock = yardBlock;
			this.blockCellNo = blockCellNo;
			this.yardLocation1 = yardLocation1;
			this.yardBlock1 = yardBlock1;
			this.blockCellNo1 = blockCellNo1;
			this.areaOccupied = areaOccupied;
			this.yardPackages = yardPackages;
			this.pod = pod;
			this.gateInType = gateInType;
			this.shift = shift;
			this.status = status;
			this.createdBy = createdBy;
			this.approvedBy = approvedBy;
			this.destuffType = destuffType;
			this.destuffFromDate = destuffFromDate;
			this.destuffToDate = destuffToDate;
			this.workOrderNo = workOrderNo;
		}


		public Destuff(String deStuffId, String createdBy, String profitcentreId, String igmTransId, String igmNo,
				String transType, String containerNo, String gateInId, String approvedBy, String status) {
			super();
			this.deStuffId = deStuffId;
			this.createdBy = createdBy;
			this.profitcentreId = profitcentreId;
			this.igmTransId = igmTransId;
			this.igmNo = igmNo;
			this.transType = transType;
			this.containerNo = containerNo;
			this.gateInId = gateInId;
			this.approvedBy = approvedBy;
			this.status = status;
		}
	    
	    

		
}
