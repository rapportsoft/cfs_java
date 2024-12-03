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
@Table(name = "cftransfersb")
@IdClass(ExportTransferId.class)
public class ExportTransfer {
	
	    @Id
	    @Column(name = "Company_Id", length = 6, nullable = false)
	    private String companyId;

	    @Id
	    @Column(name = "Branch_Id", length = 6, nullable = false)
	    private String branchId;

	    @Id
	    @Column(name = "SB_Change_Trans_Id", length = 10, nullable = false)
	    private String sbChangeTransId;

	    @Id
	    @Column(name = "Sr_No", length = 2, nullable = false)
	    private String srNo;

	    @Id
	    @Column(name = "Fin_Year", length = 4, nullable = false)
	    private String finYear;

	    @Id
	    @Column(name = "Profitcentre_Id", length = 6, nullable = false)
	    private String profitcentreId;

	    @Id
	    @Column(name = "SB_Trans_Id", length = 10, nullable = false)
	    private String sbTransId;

	    @Id
	    @Column(name = "Trf_SB_Trans_Id", length = 10, nullable = false)
	    private String trfSbTransId;

	    @Id
	    @Column(name = "SB_Line_No", length = 4, nullable = false)
	    private String sbLineNo;

	    @Id
	    @Column(name = "Trf_SB_Line_No", length = 4, nullable = false)
	    private String trfSbLineNo;

	    @Id
	    @Column(name = "SB_No", length = 15, nullable = false)
	    private String sbNo;

	    @Id
	    @Column(name = "Trf_SB_No", length = 15, nullable = false)
	    private String trfSbNo;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "SB_Change_Trans_Date", nullable = false)
	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
	    private Date sbChangeTransDate;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "SB_Trans_Date", nullable = false)
	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
	    private Date sbTransDate;

	    @Column(name = "Exporter", length = 10)
	    private String exporter;
	    
	    @Column(name = "cha", length = 10)
	    private String cha;

	    @Column(name = "On_Account_Of", length = 6)
	    private String onAccountOf;

	    @Column(name = "Transfer_Packages", precision = 8, scale = 0)
	    private BigDecimal transferPackages;

	    @Column(name = "Transfer_Gross_Weight", precision = 12, scale = 2, nullable = false)
	    private BigDecimal transferGrossWeight;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "SB_Date", nullable = false)
	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
	    private Date sbDate;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "Trf_SB_Date", nullable = false)
	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
	    private Date trfSbDate;

	    @Column(name = "No_Of_Packages", precision = 8, scale = 0, nullable = false)
	    private BigDecimal noOfPackages;

	    @Column(name = "Trf_No_Of_Packages", precision = 8, scale = 0, nullable = false)
	    private BigDecimal trfNoOfPackages;

	    @Column(name = "Gate_In_Packages", precision = 8, scale = 0)
	    private BigDecimal gateInPackages;

	    @Column(name = "Trf_Gate_In_Packages", precision = 8, scale = 0)
	    private BigDecimal trfGateInPackages;

	    @Column(name = "Carted_Packages", precision = 8, scale = 0)
	    private BigDecimal cartedPackages;

	    @Column(name = "Trf_Carted_Packages", precision = 8, scale = 0)
	    private BigDecimal trfCartedPackages;

	    @Column(name = "Nil_Packages", precision = 8, scale = 0, nullable = false)
	    private BigDecimal nilPackages;

	    @Column(name = "Gross_Weight", precision = 12, scale = 2, nullable = false)
	    private BigDecimal grossWeight;

	    @Column(name = "Trf_Gross_Weight", precision = 12, scale = 2, nullable = false)
	    private BigDecimal trfGrossWeight;

	    @Column(name = "Gate_In_Weight", precision = 12, scale = 2, nullable = false)
	    private BigDecimal gateInWeight;

	    @Column(name = "Trf_Gate_In_Weight", precision = 12, scale = 2, nullable = false)
	    private BigDecimal trfGateInWeight;

	    @Column(name = "Carted_Weight", precision = 12, scale = 2, nullable = false)
	    private BigDecimal cartedWeight;

	    @Column(name = "Trf_Carted_Weight", precision = 12, scale = 2, nullable = false)
	    private BigDecimal trfCartedWeight;

	    @Column(name = "Prev_Transfer_Pack", precision = 8, scale = 0)
	    private BigDecimal prevTransferPack;

	    @Column(name = "Stuffed_Qty", precision = 8, scale = 0)
	    private BigDecimal stuffedQty;

	    @Column(name = "Back_To_Town_Pack", precision = 8, scale = 0)
	    private BigDecimal backToTownPack;

	    @Column(name = "Commodity", length = 250, nullable = false)
	    private String commodity;

	    @Column(name = "To_Commodity", length = 250)
	    private String toCommodity;

	    @Column(name = "Comments", length = 50, nullable = false)
	    private String comments;

	    @Column(name = "Status", length = 1, nullable = false)
	    private String status;

	    @Column(name = "Created_By", length = 10, nullable = false)
	    private String createdBy;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "Created_Date", nullable = false)
	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
	    private Date createdDate;

	    @Column(name = "Edited_By", length = 10)
	    private String editedBy;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "Edited_Date")
	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
	    private Date editedDate;

	    @Column(name = "Approved_By", length = 10)
	    private String approvedBy;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "Approved_Date")
	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
	    private Date approvedDate;

	    @Column(name = "Nil_Flag", length = 1, nullable = false)
	    private String nilFlag;
	    
	    
	    transient private String exporterName;	    
	    
	    transient private String chaName;
	    
	    transient private BigDecimal preTranferedPackages;
	    
	    transient private BigDecimal toGateInPackages;	    
	    
	    transient private BigDecimal toGateInWeight;
	    
	    transient private String profitCenterName;
	    
	    transient private BigDecimal maxQuantity;
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
		public BigDecimal getMaxQuantity() {
			return maxQuantity;
		}

		public void setMaxQuantity(BigDecimal maxQuantity) {
			this.maxQuantity = maxQuantity;
		}

		public String getProfitCenterName() {
			return profitCenterName;
		}

		public void setProfitCenterName(String profitCenterName) {
			this.profitCenterName = profitCenterName;
		}

		public BigDecimal getToGateInPackages() {
			return toGateInPackages;
		}

		public void setToGateInPackages(BigDecimal toGateInPackages) {
			this.toGateInPackages = toGateInPackages;
		}

		public BigDecimal getToGateInWeight() {
			return toGateInWeight;
		}

		public void setToGateInWeight(BigDecimal toGateInWeight) {
			this.toGateInWeight = toGateInWeight;
		}

		public BigDecimal getPreTranferedPackages() {
			return preTranferedPackages;
		}

		public void setPreTranferedPackages(BigDecimal preTranferedPackages) {
			this.preTranferedPackages = preTranferedPackages;
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

		public String getSbChangeTransId() {
			return sbChangeTransId;
		}

		public void setSbChangeTransId(String sbChangeTransId) {
			this.sbChangeTransId = sbChangeTransId;
		}

		public String getSrNo() {
			return srNo;
		}

		public void setSrNo(String srNo) {
			this.srNo = srNo;
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

		public String getTrfSbTransId() {
			return trfSbTransId;
		}

		public void setTrfSbTransId(String trfSbTransId) {
			this.trfSbTransId = trfSbTransId;
		}

		public String getSbLineNo() {
			return sbLineNo;
		}

		public void setSbLineNo(String sbLineNo) {
			this.sbLineNo = sbLineNo;
		}

		public String getTrfSbLineNo() {
			return trfSbLineNo;
		}

		public void setTrfSbLineNo(String trfSbLineNo) {
			this.trfSbLineNo = trfSbLineNo;
		}

		public String getSbNo() {
			return sbNo;
		}

		public void setSbNo(String sbNo) {
			this.sbNo = sbNo;
		}

		public String getTrfSbNo() {
			return trfSbNo;
		}

		public void setTrfSbNo(String trfSbNo) {
			this.trfSbNo = trfSbNo;
		}

		public Date getSbChangeTransDate() {
			return sbChangeTransDate;
		}

		public void setSbChangeTransDate(Date sbChangeTransDate) {
			this.sbChangeTransDate = sbChangeTransDate;
		}

		public Date getSbTransDate() {
			return sbTransDate;
		}

		public void setSbTransDate(Date sbTransDate) {
			this.sbTransDate = sbTransDate;
		}

		public String getExporter() {
			return exporter;
		}

		public void setExporter(String exporter) {
			this.exporter = exporter;
		}

		public String getCha() {
			return cha;
		}

		public void setCha(String cha) {
			this.cha = cha;
		}

		public String getOnAccountOf() {
			return onAccountOf;
		}

		public void setOnAccountOf(String onAccountOf) {
			this.onAccountOf = onAccountOf;
		}

		public BigDecimal getTransferPackages() {
			return transferPackages;
		}

		public void setTransferPackages(BigDecimal transferPackages) {
			this.transferPackages = transferPackages;
		}

		public BigDecimal getTransferGrossWeight() {
			return transferGrossWeight;
		}

		public void setTransferGrossWeight(BigDecimal transferGrossWeight) {
			this.transferGrossWeight = transferGrossWeight;
		}

		public Date getSbDate() {
			return sbDate;
		}

		public void setSbDate(Date sbDate) {
			this.sbDate = sbDate;
		}

		public Date getTrfSbDate() {
			return trfSbDate;
		}

		public void setTrfSbDate(Date trfSbDate) {
			this.trfSbDate = trfSbDate;
		}

		public BigDecimal getNoOfPackages() {
			return noOfPackages;
		}

		public void setNoOfPackages(BigDecimal noOfPackages) {
			this.noOfPackages = noOfPackages;
		}

		public BigDecimal getTrfNoOfPackages() {
			return trfNoOfPackages;
		}

		public void setTrfNoOfPackages(BigDecimal trfNoOfPackages) {
			this.trfNoOfPackages = trfNoOfPackages;
		}

		public BigDecimal getGateInPackages() {
			return gateInPackages;
		}

		public void setGateInPackages(BigDecimal gateInPackages) {
			this.gateInPackages = gateInPackages;
		}

		public BigDecimal getTrfGateInPackages() {
			return trfGateInPackages;
		}

		public void setTrfGateInPackages(BigDecimal trfGateInPackages) {
			this.trfGateInPackages = trfGateInPackages;
		}

		public BigDecimal getCartedPackages() {
			return cartedPackages;
		}

		public void setCartedPackages(BigDecimal cartedPackages) {
			this.cartedPackages = cartedPackages;
		}

		public BigDecimal getTrfCartedPackages() {
			return trfCartedPackages;
		}

		public void setTrfCartedPackages(BigDecimal trfCartedPackages) {
			this.trfCartedPackages = trfCartedPackages;
		}

		public BigDecimal getNilPackages() {
			return nilPackages;
		}

		public void setNilPackages(BigDecimal nilPackages) {
			this.nilPackages = nilPackages;
		}

		public BigDecimal getGrossWeight() {
			return grossWeight;
		}

		public void setGrossWeight(BigDecimal grossWeight) {
			this.grossWeight = grossWeight;
		}

		public BigDecimal getTrfGrossWeight() {
			return trfGrossWeight;
		}

		public void setTrfGrossWeight(BigDecimal trfGrossWeight) {
			this.trfGrossWeight = trfGrossWeight;
		}

		public BigDecimal getGateInWeight() {
			return gateInWeight;
		}

		public void setGateInWeight(BigDecimal gateInWeight) {
			this.gateInWeight = gateInWeight;
		}

		public BigDecimal getTrfGateInWeight() {
			return trfGateInWeight;
		}

		public void setTrfGateInWeight(BigDecimal trfGateInWeight) {
			this.trfGateInWeight = trfGateInWeight;
		}

		public BigDecimal getCartedWeight() {
			return cartedWeight;
		}

		public void setCartedWeight(BigDecimal cartedWeight) {
			this.cartedWeight = cartedWeight;
		}

		public BigDecimal getTrfCartedWeight() {
			return trfCartedWeight;
		}

		public void setTrfCartedWeight(BigDecimal trfCartedWeight) {
			this.trfCartedWeight = trfCartedWeight;
		}

		public BigDecimal getPrevTransferPack() {
			return prevTransferPack;
		}

		public void setPrevTransferPack(BigDecimal prevTransferPack) {
			this.prevTransferPack = prevTransferPack;
		}

		public BigDecimal getStuffedQty() {
			return stuffedQty;
		}

		public void setStuffedQty(BigDecimal stuffedQty) {
			this.stuffedQty = stuffedQty;
		}

		public BigDecimal getBackToTownPack() {
			return backToTownPack;
		}

		public void setBackToTownPack(BigDecimal backToTownPack) {
			this.backToTownPack = backToTownPack;
		}

		public String getCommodity() {
			return commodity;
		}

		public void setCommodity(String commodity) {
			this.commodity = commodity;
		}

		public String getToCommodity() {
			return toCommodity;
		}

		public void setToCommodity(String toCommodity) {
			this.toCommodity = toCommodity;
		}

		public String getComments() {
			return comments;
		}

		public void setComments(String comments) {
			this.comments = comments;
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

		public String getNilFlag() {
			return nilFlag;
		}

		public void setNilFlag(String nilFlag) {
			this.nilFlag = nilFlag;
		}

		public String getExporterName() {
			return exporterName;
		}

		public void setExporterName(String exporterName) {
			this.exporterName = exporterName;
		}

		public String getChaName() {
			return chaName;
		}

		public void setChaName(String chaName) {
			this.chaName = chaName;
		}

		public ExportTransfer() {
			super();
			// TODO Auto-generated constructor stub
		}

		public ExportTransfer(String companyId, String branchId, String sbChangeTransId, String srNo, String finYear,
				String profitcentreId, String sbTransId, String trfSbTransId, String sbLineNo, String trfSbLineNo,
				String sbNo, String trfSbNo, Date sbChangeTransDate, Date sbTransDate, String exporterName,
				String onAccountOf, BigDecimal transferPackages, BigDecimal transferGrossWeight, Date sbDate,
				Date trfSbDate, BigDecimal noOfPackages, BigDecimal trfNoOfPackages, BigDecimal gateInPackages,
				BigDecimal trfGateInPackages, BigDecimal cartedPackages, BigDecimal trfCartedPackages,
				BigDecimal nilPackages, BigDecimal grossWeight, BigDecimal trfGrossWeight, BigDecimal gateInWeight,
				BigDecimal trfGateInWeight, BigDecimal cartedWeight, BigDecimal trfCartedWeight,
				BigDecimal prevTransferPack, BigDecimal stuffedQty, BigDecimal backToTownPack, String commodity,
				String toCommodity, String comments, String status, String createdBy, Date createdDate, String editedBy,
				Date editedDate, String approvedBy, Date approvedDate, String nilFlag) {
			super();
			this.companyId = companyId;
			this.branchId = branchId;
			this.sbChangeTransId = sbChangeTransId;
			this.srNo = srNo;
			this.finYear = finYear;
			this.profitcentreId = profitcentreId;
			this.sbTransId = sbTransId;
			this.trfSbTransId = trfSbTransId;
			this.sbLineNo = sbLineNo;
			this.trfSbLineNo = trfSbLineNo;
			this.sbNo = sbNo;
			this.trfSbNo = trfSbNo;
			this.sbChangeTransDate = sbChangeTransDate;
			this.sbTransDate = sbTransDate;
			this.exporterName = exporterName;
			this.onAccountOf = onAccountOf;
			this.transferPackages = transferPackages;
			this.transferGrossWeight = transferGrossWeight;
			this.sbDate = sbDate;
			this.trfSbDate = trfSbDate;
			this.noOfPackages = noOfPackages;
			this.trfNoOfPackages = trfNoOfPackages;
			this.gateInPackages = gateInPackages;
			this.trfGateInPackages = trfGateInPackages;
			this.cartedPackages = cartedPackages;
			this.trfCartedPackages = trfCartedPackages;
			this.nilPackages = nilPackages;
			this.grossWeight = grossWeight;
			this.trfGrossWeight = trfGrossWeight;
			this.gateInWeight = gateInWeight;
			this.trfGateInWeight = trfGateInWeight;
			this.cartedWeight = cartedWeight;
			this.trfCartedWeight = trfCartedWeight;
			this.prevTransferPack = prevTransferPack;
			this.stuffedQty = stuffedQty;
			this.backToTownPack = backToTownPack;
			this.commodity = commodity;
			this.toCommodity = toCommodity;
			this.comments = comments;
			this.status = status;
			this.createdBy = createdBy;
			this.createdDate = createdDate;
			this.editedBy = editedBy;
			this.editedDate = editedDate;
			this.approvedBy = approvedBy;
			this.approvedDate = approvedDate;
			this.nilFlag = nilFlag;
		}

		@Override
		public String toString() {
			return "ExportTransfer [companyId=" + companyId + ", branchId=" + branchId + ", sbChangeTransId="
					+ sbChangeTransId + ", srNo=" + srNo + ", finYear=" + finYear + ", profitcentreId=" + profitcentreId
					+ ", sbTransId=" + sbTransId + ", trfSbTransId=" + trfSbTransId + ", sbLineNo=" + sbLineNo
					+ ", trfSbLineNo=" + trfSbLineNo + ", sbNo=" + sbNo + ", trfSbNo=" + trfSbNo
					+ ", sbChangeTransDate=" + sbChangeTransDate + ", sbTransDate=" + sbTransDate + ", exporter="
					+ exporter + ", cha=" + cha + ", onAccountOf=" + onAccountOf + ", transferPackages="
					+ transferPackages + ", transferGrossWeight=" + transferGrossWeight + ", sbDate=" + sbDate
					+ ", trfSbDate=" + trfSbDate + ", noOfPackages=" + noOfPackages + ", trfNoOfPackages="
					+ trfNoOfPackages + ", gateInPackages=" + gateInPackages + ", trfGateInPackages="
					+ trfGateInPackages + ", cartedPackages=" + cartedPackages + ", trfCartedPackages="
					+ trfCartedPackages + ", nilPackages=" + nilPackages + ", grossWeight=" + grossWeight
					+ ", trfGrossWeight=" + trfGrossWeight + ", gateInWeight=" + gateInWeight + ", trfGateInWeight="
					+ trfGateInWeight + ", cartedWeight=" + cartedWeight + ", trfCartedWeight=" + trfCartedWeight
					+ ", prevTransferPack=" + prevTransferPack + ", stuffedQty=" + stuffedQty + ", backToTownPack="
					+ backToTownPack + ", commodity=" + commodity + ", toCommodity=" + toCommodity + ", comments="
					+ comments + ", status=" + status + ", createdBy=" + createdBy + ", createdDate=" + createdDate
					+ ", editedBy=" + editedBy + ", editedDate=" + editedDate + ", approvedBy=" + approvedBy
					+ ", approvedDate=" + approvedDate + ", nilFlag=" + nilFlag + "]";
		}

		public ExportTransfer(String sbChangeTransId, String profitcentreId, String sbTransId, String trfSbTransId,
				String sbNo, String trfSbNo, Date sbChangeTransDate, Date sbDate, Date trfSbDate, String status,
				String profitCenterName, String srNo) {
			super();
			this.sbChangeTransId = sbChangeTransId;
			this.profitcentreId = profitcentreId;
			this.sbTransId = sbTransId;
			this.trfSbTransId = trfSbTransId;
			this.sbNo = sbNo;
			this.trfSbNo = trfSbNo;
			this.sbChangeTransDate = sbChangeTransDate;
			this.sbDate = sbDate;
			this.trfSbDate = trfSbDate;
			this.status = status;
			this.profitCenterName = profitCenterName;
			this.srNo = srNo;
		}

		public ExportTransfer(String companyId, String branchId, String sbChangeTransId, String srNo, String finYear,
				String profitcentreId, String sbTransId, String trfSbTransId, String sbLineNo, String trfSbLineNo,
				String sbNo, String trfSbNo, Date sbChangeTransDate, Date sbTransDate, String exporter, String cha,
				String onAccountOf, BigDecimal transferPackages, BigDecimal transferGrossWeight, Date sbDate,
				Date trfSbDate, BigDecimal noOfPackages, BigDecimal trfNoOfPackages, BigDecimal gateInPackages,
				BigDecimal trfGateInPackages, BigDecimal cartedPackages, BigDecimal trfCartedPackages,
				BigDecimal nilPackages, BigDecimal grossWeight, BigDecimal trfGrossWeight, BigDecimal gateInWeight,
				BigDecimal trfGateInWeight, BigDecimal cartedWeight, BigDecimal trfCartedWeight,
				BigDecimal prevTransferPack, BigDecimal stuffedQty, BigDecimal backToTownPack, String commodity,
				String toCommodity, String comments, String status, String createdBy, Date createdDate, String editedBy,
				Date editedDate, String approvedBy, Date approvedDate, String nilFlag, String exporterName,
				String chaName) {
			super();
			this.companyId = companyId;
			this.branchId = branchId;
			this.sbChangeTransId = sbChangeTransId;
			this.srNo = srNo;
			this.finYear = finYear;
			this.profitcentreId = profitcentreId;
			this.sbTransId = sbTransId;
			this.trfSbTransId = trfSbTransId;
			this.sbLineNo = sbLineNo;
			this.trfSbLineNo = trfSbLineNo;
			this.sbNo = sbNo;
			this.trfSbNo = trfSbNo;
			this.sbChangeTransDate = sbChangeTransDate;
			this.sbTransDate = sbTransDate;
			this.exporter = exporter;
			this.cha = cha;
			this.onAccountOf = onAccountOf;
			this.transferPackages = transferPackages;
			this.transferGrossWeight = transferGrossWeight;
			this.sbDate = sbDate;
			this.trfSbDate = trfSbDate;
			this.noOfPackages = noOfPackages;
			this.trfNoOfPackages = trfNoOfPackages;
			this.gateInPackages = gateInPackages;
			this.trfGateInPackages = trfGateInPackages;
			this.cartedPackages = cartedPackages;
			this.trfCartedPackages = trfCartedPackages;
			this.nilPackages = nilPackages;
			this.grossWeight = grossWeight;
			this.trfGrossWeight = trfGrossWeight;
			this.gateInWeight = gateInWeight;
			this.trfGateInWeight = trfGateInWeight;
			this.cartedWeight = cartedWeight;
			this.trfCartedWeight = trfCartedWeight;
			this.prevTransferPack = prevTransferPack;
			this.stuffedQty = stuffedQty;
			this.backToTownPack = backToTownPack;
			this.commodity = commodity;
			this.toCommodity = toCommodity;
			this.comments = comments;
			this.status = status;
			this.createdBy = createdBy;
			this.createdDate = createdDate;
			this.editedBy = editedBy;
			this.editedDate = editedDate;
			this.approvedBy = approvedBy;
			this.approvedDate = approvedDate;
			this.nilFlag = nilFlag;
			this.exporterName = exporterName;
			this.chaName = chaName;
			
		}

		
		
//		Export Main Search
		public ExportTransfer(String sbChangeTransId, String srNo) {
			super();
			this.sbChangeTransId = sbChangeTransId;
			this.srNo = srNo;
		}

	    
	    
	    
}
