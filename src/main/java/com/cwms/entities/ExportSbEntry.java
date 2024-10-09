package com.cwms.entities;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;

@Entity
@Table(name = "cfsb")
@IdClass(ExportSbEntryId.class)
public class ExportSbEntry {	

    @Id
    @Column(name = "Company_Id", length = 6)
    private String companyId;

    @Id
    @Column(name = "Branch_Id", length = 6)
    private String branchId;

    @Id
    @Column(name = "Fin_Year", length = 6)
    private String finYear;

    @Id
    @Column(name = "Profitcentre_Id", length = 10)
    private String profitcentreId;

    @Id
    @Column(name = "SB_Trans_Id", length = 10)
    private String sbTransId;

    @Id
    @Column(name = "SB_No", length = 10)
    private String sbNo;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @Column(name = "SB_Trans_Date")
    private Date sbTransDate;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @Column(name = "SB_Date")
    private Date sbDate;

    @Column(name = "IEC_Code", length = 25)
    private String iecCode;

    @Column(name = "State", length = 2)
    private String state;

    @Column(name = "GST_No", length = 30)
    private String gstNo;

    @Column(name = "Sr_No", length = 2, columnDefinition = "char(2) DEFAULT '1'")
    private String srNo;

    @Column(name = "Exporter_Id", length = 10)
    private String exporterId;

    @Column(name = "Exporter_Name", length = 100)
    private String exporterName;

    @Column(name = "Exporter_Address1", length = 250)
    private String exporterAddress1;

    @Column(name = "Exporter_Address2", length = 100)
    private String exporterAddress2;

    @Column(name = "Exporter_Address3", length = 60)
    private String exporterAddress3;

    @Column(name = "Consignee_Name", length = 60)
    private String consigneeName;

    @Column(name = "Consignee_Address1", length = 250)
    private String consigneeAddress1;

    @Column(name = "Consignee_Address2", length = 60)
    private String consigneeAddress2;

    @Column(name = "Consignee_Address3", length = 60)
    private String consigneeAddress3;

    @Column(name = "sb_type", length = 20, columnDefinition = "varchar(20) DEFAULT 'Normal'")
    private String sbType;

    @Column(name = "Cargo_Type", length = 6, columnDefinition = "varchar(6) DEFAULT 'NAGRO'")
    private String cargoType;

    @Column(name = "cargo_Loc", length = 15, columnDefinition = "varchar(15) DEFAULT 'WH'")
    private String cargoLoc;

    @Column(name = "CHA", length = 6)
    private String cha;
    
//    @Column(name = "CHA_CODE", length = 15)
//    private String chaCode;

    @Column(name = "POL", length = 6)
    private String pol;

    @Column(name = "POD", length = 40)
    private String pod;

    @Column(name = "Destination_Country", length = 40)
    private String destinationCountry;

    @Column(name = "Total_Packages", precision = 8, scale = 0, columnDefinition = "decimal(8,0) DEFAULT '0'")
    private BigDecimal totalPackages;

    @Column(name = "Gate_In_Packages", precision = 8, scale = 0, columnDefinition = "decimal(8,0) DEFAULT '0'")
    private BigDecimal gateInPackages;

    @Column(name = "Stuffed_Qty", precision = 8, scale = 0, columnDefinition = "decimal(8,0) DEFAULT '0'")
    private BigDecimal stuffedQty;

    @Column(name = "Total_Gross_Weight", precision = 16, scale = 4, columnDefinition = "decimal(16,4) DEFAULT '0.0000'")
    private BigDecimal totalGrossWeight;

    @Column(name = "Haz", length = 1, columnDefinition = "char(1) DEFAULT ''")
    private String haz;

    @Column(name = "IMO_Code", length = 20)
    private String imoCode;

    @Column(name = "Comments", length = 150, columnDefinition = "varchar(150) DEFAULT ''")
    private String comments;

    @Column(name = "Out_Of_Charge", length = 1, columnDefinition = "char(1) DEFAULT 'N'")
    private String outOfCharge;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @Column(name = "Out_Of_Charge_Date")
    private Date outOfChargeDate;

    @Column(name = "On_Account_Of", length = 6)
    private String onAccountOf;
    
//    
//    @Column(name = "On_Account_Of_Code", length = 15)
//    private String onAccountOfCode;

    @Column(name = "Draw_Back_Value", precision = 12, scale = 2, columnDefinition = "decimal(12,2) DEFAULT '0.00'")
    private BigDecimal drawBackValue;

    @Column(name = "Status", length = 1, columnDefinition = "char(1) DEFAULT ''")
    private String status;

    @Column(name = "Created_By", length = 10)
    private String createdBy;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @Column(name = "Created_Date")
    private Date createdDate;

    @Column(name = "Edited_By", length = 10)
    private String editedBy;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @Column(name = "Edited_Date")
    private Date editedDate;

    @Column(name = "Approved_By", length = 10)
    private String approvedBy;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @Column(name = "Approved_Date")
    private Date approvedDate;

    @Column(name = "LEO_No", length = 20, nullable = false)
    private String leoNo;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @Column(name = "LEO_Date")
    private Date leoDate;

    @Column(name = "MPCIN", length = 30, nullable = false)
    private String mpcin;

    @Column(name = "GatewayPort", length = 10, nullable = false)
    private String gatewayPort;

    @Column(name = "Pay_Load", columnDefinition = "longtext")
    private String payLoad;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @Column(name = "MPCIN_Read_Date")
    private Date mpcinReadDate;

    @Column(name = "Hold_Status", length = 1, columnDefinition = "char(1) DEFAULT 'N'")
    private String holdStatus;

    @Column(name = "Holding_Agent_Name", length = 35)
    private String holdingAgentName;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @Column(name = "Hold_Date")
    private Date holdDate;

    @Column(name = "Hold_Remarks", length = 150)
    private String holdRemarks;

    @Column(name = "Release_Agent", length = 35)
    private String releaseAgent;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @Column(name = "Release_Date")
    private Date releaseDate;

    @Column(name = "Release_Remarks", length = 150)
    private String releaseRemarks;

    @Column(name = "H_sb_trans_id", length = 10)
    private String hSbTransId;

    @Column(name = "SSR_Trans_Id", length = 20, nullable = false)
    private String ssrTransId;

    @Column(name = "Document_Upload_Flag", length = 1, columnDefinition = "char(1) DEFAULT 'N'")
    private String documentUploadFlag;

	public ExportSbEntry() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ExportSbEntry(String companyId, String branchId, String finYear, String profitcentreId, String sbTransId,
			String sbNo, Date sbTransDate, Date sbDate, String iecCode, String state, String gstNo, String srNo,
			String importerId, String exporterName, String exporterAddress1, String exporterAddress2,
			String exporterAddress3, String consigneeName, String consigneeAddress1, String consigneeAddress2,
			String consigneeAddress3, String sbType, String cargoType, String cargoLoc, String cha, String pol,
			String pod, String destinationCountry, BigDecimal totalPackages, BigDecimal gateInPackages,
			BigDecimal stuffedQty, BigDecimal totalGrossWeight, String haz, String imoCode, String comments,
			String outOfCharge, Date outOfChargeDate, String onAccountOf, BigDecimal drawBackValue, String status,
			String createdBy, Date createdDate, String editedBy, Date editedDate, String approvedBy, Date approvedDate,
			String leoNo, Date leoDate, String mpcin, String gatewayPort, String payLoad, Date mpcinReadDate,
			String holdStatus, String holdingAgentName, Date holdDate, String holdRemarks, String releaseAgent,
			Date releaseDate, String releaseRemarks, String hSbTransId, String ssrTransId, String documentUploadFlag) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.finYear = finYear;
		this.profitcentreId = profitcentreId;
		this.sbTransId = sbTransId;
		this.sbNo = sbNo;
		this.sbTransDate = sbTransDate;
		this.sbDate = sbDate;
		this.iecCode = iecCode;
		this.state = state;
		this.gstNo = gstNo;
		this.srNo = srNo;
		this.exporterId = importerId;
		this.exporterName = exporterName;
		this.exporterAddress1 = exporterAddress1;
		this.exporterAddress2 = exporterAddress2;
		this.exporterAddress3 = exporterAddress3;
		this.consigneeName = consigneeName;
		this.consigneeAddress1 = consigneeAddress1;
		this.consigneeAddress2 = consigneeAddress2;
		this.consigneeAddress3 = consigneeAddress3;
		this.sbType = sbType;
		this.cargoType = cargoType;
		this.cargoLoc = cargoLoc;
		this.cha = cha;
		this.pol = pol;
		this.pod = pod;
		this.destinationCountry = destinationCountry;
		this.totalPackages = totalPackages;
		this.gateInPackages = gateInPackages;
		this.stuffedQty = stuffedQty;
		this.totalGrossWeight = totalGrossWeight;
		this.haz = haz;
		this.imoCode = imoCode;
		this.comments = comments;
		this.outOfCharge = outOfCharge;
		this.outOfChargeDate = outOfChargeDate;
		this.onAccountOf = onAccountOf;
		this.drawBackValue = drawBackValue;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.editedBy = editedBy;
		this.editedDate = editedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.leoNo = leoNo;
		this.leoDate = leoDate;
		this.mpcin = mpcin;
		this.gatewayPort = gatewayPort;
		this.payLoad = payLoad;
		this.mpcinReadDate = mpcinReadDate;
		this.holdStatus = holdStatus;
		this.holdingAgentName = holdingAgentName;
		this.holdDate = holdDate;
		this.holdRemarks = holdRemarks;
		this.releaseAgent = releaseAgent;
		this.releaseDate = releaseDate;
		this.releaseRemarks = releaseRemarks;
		this.hSbTransId = hSbTransId;
		this.ssrTransId = ssrTransId;
		this.documentUploadFlag = documentUploadFlag;
	}
	
//	public String getOnAccountOfCode() {
//		return onAccountOfCode;
//	}
//
//	public void setOnAccountOfCode(String onAccountOfCode) {
//		this.onAccountOfCode = onAccountOfCode;
//	}
//
//	public String getChaCode() {
//		return chaCode;
//	}
//
//	public void setChaCode(String chaCode) {
//		this.chaCode = chaCode;
//	}

	public void setExporterId(String exporterId) {
		this.exporterId = exporterId;
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

	public String getProfitcentreId() {
		return profitcentreId;
	}

	public void setProfitcentreId(String profitcentreId) {
		this.profitcentreId = profitcentreId;
	}

	public String getSbTransId() {
		return sbTransId;
	}

	public void setSbTransId(String sbTransId) {
		this.sbTransId = sbTransId;
	}

	public String getSbNo() {
		return sbNo;
	}

	public void setSbNo(String sbNo) {
		this.sbNo = sbNo;
	}

	public Date getSbTransDate() {
		return sbTransDate;
	}

	public void setSbTransDate(Date sbTransDate) {
		this.sbTransDate = sbTransDate;
	}

	public Date getSbDate() {
		return sbDate;
	}

	public void setSbDate(Date sbDate) {
		this.sbDate = sbDate;
	}

	public String getIecCode() {
		return iecCode;
	}

	public void setIecCode(String iecCode) {
		this.iecCode = iecCode;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getGstNo() {
		return gstNo;
	}

	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
	}

	public String getSrNo() {
		return srNo;
	}

	public void setSrNo(String srNo) {
		this.srNo = srNo;
	}

	public String getExporterId() {
		return exporterId;
	}

	public void setImporterId(String exporterId) {
		this.exporterId = exporterId;
	}

	public String getExporterName() {
		return exporterName;
	}

	public void setExporterName(String exporterName) {
		this.exporterName = exporterName;
	}

	public String getExporterAddress1() {
		return exporterAddress1;
	}

	public void setExporterAddress1(String exporterAddress1) {
		this.exporterAddress1 = exporterAddress1;
	}

	public String getExporterAddress2() {
		return exporterAddress2;
	}

	public void setExporterAddress2(String exporterAddress2) {
		this.exporterAddress2 = exporterAddress2;
	}

	public String getExporterAddress3() {
		return exporterAddress3;
	}

	public void setExporterAddress3(String exporterAddress3) {
		this.exporterAddress3 = exporterAddress3;
	}

	public String getConsigneeName() {
		return consigneeName;
	}

	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}

	public String getConsigneeAddress1() {
		return consigneeAddress1;
	}

	public void setConsigneeAddress1(String consigneeAddress1) {
		this.consigneeAddress1 = consigneeAddress1;
	}

	public String getConsigneeAddress2() {
		return consigneeAddress2;
	}

	public void setConsigneeAddress2(String consigneeAddress2) {
		this.consigneeAddress2 = consigneeAddress2;
	}

	public String getConsigneeAddress3() {
		return consigneeAddress3;
	}

	public void setConsigneeAddress3(String consigneeAddress3) {
		this.consigneeAddress3 = consigneeAddress3;
	}

	public String getSbType() {
		return sbType;
	}

	public void setSbType(String sbType) {
		this.sbType = sbType;
	}

	public String getCargoType() {
		return cargoType;
	}

	public void setCargoType(String cargoType) {
		this.cargoType = cargoType;
	}

	public String getCargoLoc() {
		return cargoLoc;
	}

	public void setCargoLoc(String cargoLoc) {
		this.cargoLoc = cargoLoc;
	}

	public String getCha() {
		return cha;
	}

	public void setCha(String cha) {
		this.cha = cha;
	}

	public String getPol() {
		return pol;
	}

	public void setPol(String pol) {
		this.pol = pol;
	}

	public String getPod() {
		return pod;
	}

	public void setPod(String pod) {
		this.pod = pod;
	}

	public String getDestinationCountry() {
		return destinationCountry;
	}

	public void setDestinationCountry(String destinationCountry) {
		this.destinationCountry = destinationCountry;
	}

	public BigDecimal getTotalPackages() {
		return totalPackages;
	}

	public void setTotalPackages(BigDecimal totalPackages) {
		this.totalPackages = totalPackages;
	}

	public BigDecimal getGateInPackages() {
		return gateInPackages;
	}

	public void setGateInPackages(BigDecimal gateInPackages) {
		this.gateInPackages = gateInPackages;
	}

	public BigDecimal getStuffedQty() {
		return stuffedQty;
	}

	public void setStuffedQty(BigDecimal stuffedQty) {
		this.stuffedQty = stuffedQty;
	}

	public BigDecimal getTotalGrossWeight() {
		return totalGrossWeight;
	}

	public void setTotalGrossWeight(BigDecimal totalGrossWeight) {
		this.totalGrossWeight = totalGrossWeight;
	}

	public String getHaz() {
		return haz;
	}

	public void setHaz(String haz) {
		this.haz = haz;
	}

	public String getImoCode() {
		return imoCode;
	}

	public void setImoCode(String imoCode) {
		this.imoCode = imoCode;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getOutOfCharge() {
		return outOfCharge;
	}

	public void setOutOfCharge(String outOfCharge) {
		this.outOfCharge = outOfCharge;
	}

	public Date getOutOfChargeDate() {
		return outOfChargeDate;
	}

	public void setOutOfChargeDate(Date outOfChargeDate) {
		this.outOfChargeDate = outOfChargeDate;
	}

	public String getOnAccountOf() {
		return onAccountOf;
	}

	public void setOnAccountOf(String onAccountOf) {
		this.onAccountOf = onAccountOf;
	}

	public BigDecimal getDrawBackValue() {
		return drawBackValue;
	}

	public void setDrawBackValue(BigDecimal drawBackValue) {
		this.drawBackValue = drawBackValue;
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

	public String getLeoNo() {
		return leoNo;
	}

	public void setLeoNo(String leoNo) {
		this.leoNo = leoNo;
	}

	public Date getLeoDate() {
		return leoDate;
	}

	public void setLeoDate(Date leoDate) {
		this.leoDate = leoDate;
	}

	public String getMpcin() {
		return mpcin;
	}

	public void setMpcin(String mpcin) {
		this.mpcin = mpcin;
	}

	public String getGatewayPort() {
		return gatewayPort;
	}

	public void setGatewayPort(String gatewayPort) {
		this.gatewayPort = gatewayPort;
	}

	public String getPayLoad() {
		return payLoad;
	}

	public void setPayLoad(String payLoad) {
		this.payLoad = payLoad;
	}

	public Date getMpcinReadDate() {
		return mpcinReadDate;
	}

	public void setMpcinReadDate(Date mpcinReadDate) {
		this.mpcinReadDate = mpcinReadDate;
	}

	public String getHoldStatus() {
		return holdStatus;
	}

	public void setHoldStatus(String holdStatus) {
		this.holdStatus = holdStatus;
	}

	public String getHoldingAgentName() {
		return holdingAgentName;
	}

	public void setHoldingAgentName(String holdingAgentName) {
		this.holdingAgentName = holdingAgentName;
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

	public String getReleaseAgent() {
		return releaseAgent;
	}

	public void setReleaseAgent(String releaseAgent) {
		this.releaseAgent = releaseAgent;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getReleaseRemarks() {
		return releaseRemarks;
	}

	public void setReleaseRemarks(String releaseRemarks) {
		this.releaseRemarks = releaseRemarks;
	}

	public String gethSbTransId() {
		return hSbTransId;
	}

	public void sethSbTransId(String hSbTransId) {
		this.hSbTransId = hSbTransId;
	}

	public String getSsrTransId() {
		return ssrTransId;
	}

	public void setSsrTransId(String ssrTransId) {
		this.ssrTransId = ssrTransId;
	}

	public String getDocumentUploadFlag() {
		return documentUploadFlag;
	}

	public void setDocumentUploadFlag(String documentUploadFlag) {
		this.documentUploadFlag = documentUploadFlag;
	}

	@Override
	public String toString() {
		return "ExportSbEntry [companyId=" + companyId + ", branchId=" + branchId + ", finYear=" + finYear
				+ ", profitcentreId=" + profitcentreId + ", sbTransId=" + sbTransId + ", sbNo=" + sbNo
				+ ", sbTransDate=" + sbTransDate + ", sbDate=" + sbDate + ", iecCode=" + iecCode + ", state=" + state
				+ ", gstNo=" + gstNo + ", srNo=" + srNo + ", exporterId=" + exporterId + ", exporterName="
				+ exporterName + ", exporterAddress1=" + exporterAddress1 + ", exporterAddress2=" + exporterAddress2
				+ ", exporterAddress3=" + exporterAddress3 + ", consigneeName=" + consigneeName + ", consigneeAddress1="
				+ consigneeAddress1 + ", consigneeAddress2=" + consigneeAddress2 + ", consigneeAddress3="
				+ consigneeAddress3 + ", sbType=" + sbType + ", cargoType=" + cargoType + ", cargoLoc=" + cargoLoc
				+ ", cha=" + cha + ", pol=" + pol + ", pod=" + pod + ", destinationCountry=" + destinationCountry
				+ ", totalPackages=" + totalPackages + ", gateInPackages=" + gateInPackages + ", stuffedQty="
				+ stuffedQty + ", totalGrossWeight=" + totalGrossWeight + ", haz=" + haz + ", imoCode=" + imoCode
				+ ", comments=" + comments + ", outOfCharge=" + outOfCharge + ", outOfChargeDate=" + outOfChargeDate
				+ ", onAccountOf=" + onAccountOf + ", drawBackValue=" + drawBackValue + ", status=" + status
				+ ", createdBy=" + createdBy + ", createdDate=" + createdDate + ", editedBy=" + editedBy
				+ ", editedDate=" + editedDate + ", approvedBy=" + approvedBy + ", approvedDate=" + approvedDate
				+ ", leoNo=" + leoNo + ", leoDate=" + leoDate + ", mpcin=" + mpcin + ", gatewayPort=" + gatewayPort
				+ ", payLoad=" + payLoad + ", mpcinReadDate=" + mpcinReadDate + ", holdStatus=" + holdStatus
				+ ", holdingAgentName=" + holdingAgentName + ", holdDate=" + holdDate + ", holdRemarks=" + holdRemarks
				+ ", releaseAgent=" + releaseAgent + ", releaseDate=" + releaseDate + ", releaseRemarks="
				+ releaseRemarks + ", hSbTransId=" + hSbTransId + ", ssrTransId=" + ssrTransId + ", documentUploadFlag="
				+ documentUploadFlag + "]";
	}

	public ExportSbEntry(String sbTransId, String sbNo, String pod, BigDecimal totalPackages, BigDecimal gateInPackages,
			String hSbTransId, String profitcentreId) {
		super();
		this.sbTransId = sbTransId;
		this.sbNo = sbNo;
		this.pod = pod;
		this.totalPackages = totalPackages;
		this.gateInPackages = gateInPackages;
		this.hSbTransId = hSbTransId;
		this.profitcentreId = profitcentreId;
	}
    
    
	public ExportSbEntry(String sbTransId, String sbNo, String profitcentreId, String outOfCharge, Date outOfChargeDate,
			String hSbTransId, Date leoDate) {
		super();
		this.sbTransId = sbTransId;
		this.sbNo = sbNo;
		this.profitcentreId = profitcentreId;
		this.outOfCharge = outOfCharge;
		this.outOfChargeDate = outOfChargeDate;
		this.hSbTransId = hSbTransId;
		this.leoDate = leoDate;
	}
    
	

}
