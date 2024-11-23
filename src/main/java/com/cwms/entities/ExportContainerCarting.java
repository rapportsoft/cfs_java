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
import jakarta.persistence.Transient;

@Entity
@Table(name="cfcrtgcn")
@IdClass(ExportContainerCartingId.class)
public class ExportContainerCarting {

	@Id
	@Column(name="Company_Id",length = 6)
	private String companyId;
	
	@Id
	@Column(name="Branch_Id",length = 6)
	private String branchId;
	
	@Id
	@Column(name="De_Stuff_Id",length = 10)
	private String deStuffId;
	
	@Column(name="De_Stuff_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date deStuffDate;
	
	@Column(name="Profitcentre_Id",length = 6)
	private String profitCentreId;
	
    @Column(name = "IGM_Trans_Id", length = 10)
    private String igmTransId;

    @Column(name = "IGM_No", length = 5)
    private String igmNo;

    @Column(name = "IGM_Line_No", length = 5)
    private String igmLineNo;

    @Temporal(TemporalType.DATE)
    @Column(name = "IGM_Date")
    private Date igmDate;

    @Column(name = "Trans_type", length = 5)
    private String transType;

    @Column(name = "VIA_No", length = 7)
    private String viaNo;

    @Column(name = "Shipping_Agent", length = 6)
    private String shippingAgent;

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

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "Gate_In_Date")
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
    
    @Column(name = "Vehicle_No", length = 15)
    private String vehicleNo;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "Approved_Date")
    private Date approvedDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "From_Date")
    private Date fromDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "To_Date")
    private Date toDate;

    @Column(name = "Remark", length = 200)
    private String remark;

    @Column(name = "Container_SearchType", length = 50)
    private String containerSearchType;
    
    @Transient
    private String onAccountOfName;
    
    @Transient
    private String saName;

	public ExportContainerCarting() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	
	


	public String getSaName() {
		return saName;
	}







	public void setSaName(String saName) {
		this.saName = saName;
	}













	public ExportContainerCarting(String companyId, String branchId, String deStuffId, Date deStuffDate,
			String profitCentreId, String igmTransId, String igmNo, String igmLineNo, Date igmDate, String transType,
			String viaNo, String shippingAgent, String containerNo, String containerType, String containerSize,
			String containerStatus, String haz, BigDecimal grossWeight, String containerSealNo, String customSealNo,
			String saSealNo, String onAccountOf, String gateInId, Date gateInDate, String yardLocation,
			String yardBlock, String blockCellNo, String yardLocation1, String yardBlock1, String blockCellNo1,
			BigDecimal areaOccupied, BigDecimal yardPackages, String pod, String invoiceType, String gateInType,
			String shift, String status, String createdBy, Date createdDate, String editedBy, Date editedDate,
			String approvedBy, String vehicleNo, Date approvedDate, Date fromDate, Date toDate, String remark,
			String containerSearchType, String onAccountOfName, String saName) {
		this.companyId = companyId;
		this.branchId = branchId;
		this.deStuffId = deStuffId;
		this.deStuffDate = deStuffDate;
		this.profitCentreId = profitCentreId;
		this.igmTransId = igmTransId;
		this.igmNo = igmNo;
		this.igmLineNo = igmLineNo;
		this.igmDate = igmDate;
		this.transType = transType;
		this.viaNo = viaNo;
		this.shippingAgent = shippingAgent;
		this.containerNo = containerNo;
		this.containerType = containerType;
		this.containerSize = containerSize;
		this.containerStatus = containerStatus;
		this.haz = haz;
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
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.editedBy = editedBy;
		this.editedDate = editedDate;
		this.approvedBy = approvedBy;
		this.vehicleNo = vehicleNo;
		this.approvedDate = approvedDate;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.remark = remark;
		this.containerSearchType = containerSearchType;
		this.onAccountOfName = onAccountOfName;
		this.saName = saName;
	}







	public String getOnAccountOfName() {
		return onAccountOfName;
	}







	public void setOnAccountOfName(String onAccountOfName) {
		this.onAccountOfName = onAccountOfName;
	}







	public String getVehicleNo() {
		return vehicleNo;
	}



	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
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

	public String getProfitCentreId() {
		return profitCentreId;
	}

	public void setProfitCentreId(String profitCentreId) {
		this.profitCentreId = profitCentreId;
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

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getContainerSearchType() {
		return containerSearchType;
	}

	public void setContainerSearchType(String containerSearchType) {
		this.containerSearchType = containerSearchType;
	}

	@Override
	public String toString() {
		return "ExportContainerCarting [companyId=" + companyId + ", branchId=" + branchId + ", deStuffId=" + deStuffId
				+ ", deStuffDate=" + deStuffDate + ", profitCentreId=" + profitCentreId + ", igmTransId=" + igmTransId
				+ ", igmNo=" + igmNo + ", igmLineNo=" + igmLineNo + ", igmDate=" + igmDate + ", transType=" + transType
				+ ", viaNo=" + viaNo + ", shippingAgent=" + shippingAgent + ", containerNo=" + containerNo
				+ ", containerType=" + containerType + ", containerSize=" + containerSize + ", containerStatus="
				+ containerStatus + ", haz=" + haz + ", grossWeight=" + grossWeight + ", containerSealNo="
				+ containerSealNo + ", customSealNo=" + customSealNo + ", saSealNo=" + saSealNo + ", onAccountOf="
				+ onAccountOf + ", gateInId=" + gateInId + ", gateInDate=" + gateInDate + ", yardLocation="
				+ yardLocation + ", yardBlock=" + yardBlock + ", blockCellNo=" + blockCellNo + ", yardLocation1="
				+ yardLocation1 + ", yardBlock1=" + yardBlock1 + ", blockCellNo1=" + blockCellNo1 + ", areaOccupied="
				+ areaOccupied + ", yardPackages=" + yardPackages + ", pod=" + pod + ", invoiceType=" + invoiceType
				+ ", gateInType=" + gateInType + ", shift=" + shift + ", status=" + status + ", createdBy=" + createdBy
				+ ", createdDate=" + createdDate + ", editedBy=" + editedBy + ", editedDate=" + editedDate
				+ ", approvedBy=" + approvedBy + ", approvedDate=" + approvedDate + ", fromDate=" + fromDate
				+ ", toDate=" + toDate + ", remark=" + remark + ", containerSearchType=" + containerSearchType + "]";
	}







	public ExportContainerCarting(String deStuffId, Date deStuffDate, String profitCentreId, String shippingAgent,
			String containerNo, String containerType, String containerSize, String containerStatus, String customSealNo,
			String saSealNo, String onAccountOf, String gateInId, Date gateInDate, String status, String createdBy,
			String vehicleNo, Date fromDate, Date toDate, String remark, String containerSearchType,
			String onAccountOfName, String saName) {
		this.deStuffId = deStuffId;
		this.deStuffDate = deStuffDate;
		this.profitCentreId = profitCentreId;
		this.shippingAgent = shippingAgent;
		this.containerNo = containerNo;
		this.containerType = containerType;
		this.containerSize = containerSize;
		this.containerStatus = containerStatus;
		this.customSealNo = customSealNo;
		this.saSealNo = saSealNo;
		this.onAccountOf = onAccountOf;
		this.gateInId = gateInId;
		this.gateInDate = gateInDate;
		this.status = status;
		this.createdBy = createdBy;
		this.vehicleNo = vehicleNo;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.remark = remark;
		this.containerSearchType = containerSearchType;
		this.onAccountOfName = onAccountOfName;
		this.saName = saName;
	}
    
    
	
	
    
}
