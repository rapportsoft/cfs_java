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
@Table(name = "cftransfersbdtl")
@IdClass(ExportTransferDetailId.class)
public class ExportTransferDetail {

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
    @Column(name = "SB_Change_Trans_Id", length = 10, nullable = false)
    private String sbChangeTransId;

	@Id
    @Column(name = "Trans_Line_Id", length = 4, nullable = false)
    private String transLineId;

	@Id
    @Column(name = "Carting_Trans_Id", length = 10, nullable = false)
    private String cartingTransId;

	@Id
    @Column(name = "Carting_Line_Id", length = 4, nullable = false)
    private String cartingLineId;

	@Id
    @Column(name = "SB_Trans_Id", length = 10, nullable = false)
    private String sbTransId;

	@Id
    @Column(name = "Vehicle_No", length = 15, nullable = false)
    private String vehicleNo;

	@Id
    @Column(name = "Gate_In_Id", length = 10, nullable = false)
    private String gateInId;
    
    @Column(name = "SB_No", length = 25)
    private String sbNo;

    @Column(name = "SB_Line_No", length = 6, nullable = false)
    private String sbLineNo;

    @Column(name = "Trf_SB_Trans_Id", length = 10, nullable = false)
    private String trfSbTransId;

    @Column(name = "Trf_SB_No", length = 25)
    private String trfSbNo;

    @Column(name = "Trf_SB_Line_No", length = 6, nullable = false)
    private String trfSbLineNo;

    @Column(name = "Carting_Packages", precision = 8, scale = 0)
    private BigDecimal cartingPackages;

    @Column(name = "Out_pkgs", precision = 8, scale = 0)
    private BigDecimal outPkgs;

    @Column(name = "Balance_pkgs", precision = 8, scale = 0)
    private BigDecimal balancePkgs;

    @Column(name = "Nil_Packages", precision = 8, scale = 0)
    private BigDecimal nilPackages;

    @Column(name = "Carted_Weight", precision = 12, scale = 2)
    private BigDecimal cartedWeight;
    
    @Column(name = "Trf_Carted_Weight", precision = 12, scale = 2)
    private BigDecimal trfCartedWeight;

    @Column(name = "Trf_Carted_Packages", precision = 8, scale = 0)
    private BigDecimal trfCartedPackages;

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
    @Column(name = "Edited_Date", nullable = true)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date editedDate;

    @Column(name = "Approved_By", length = 10)
    private String approvedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "Approved_Date", nullable = true)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date approvedDate;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "Gate_In_Date", nullable = true)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date gateInDate;
    
    

    @Column(name = "Gate_In_Packages", precision = 8, scale = 0)
    private BigDecimal gateInPackages;
    
    @Column(name = "Trf_Gate_In_Packages", precision = 8, scale = 0)
    private BigDecimal trfGateInPackages;
    
    @Column(name = "Gate_In_Weight", precision = 20, scale = 4)
    private BigDecimal gateInWeight;
    
    @Column(name = "Trf_Gate_In_Weight", precision = 20, scale = 4)
    private BigDecimal trfGateInWeight;
    
    
    transient private String cha ;
    transient private String onAccountOf;
    transient private BigDecimal grossWeight;
    transient private BigDecimal actualNoOfPackages;
    
    
    
    
    

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

	public BigDecimal getActualNoOfPackages() {
		return actualNoOfPackages;
	}

	public void setActualNoOfPackages(BigDecimal actualNoOfPackages) {
		this.actualNoOfPackages = actualNoOfPackages;
	}

	public BigDecimal getGrossWeight() {
		return grossWeight;
	}

	public void setGrossWeight(BigDecimal grossWeight) {
		this.grossWeight = grossWeight;
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

	public ExportTransferDetail() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public ExportTransferDetail(String companyId, String branchId, String finYear, String sbChangeTransId,
			String transLineId, String cartingTransId, String cartingLineId, String sbTransId, String vehicleNo,
			String gateInId, String sbNo, String sbLineNo, String trfSbTransId, String trfSbNo, String trfSbLineNo,
			BigDecimal cartingPackages, BigDecimal outPkgs, BigDecimal balancePkgs, BigDecimal nilPackages,
			BigDecimal cartedWeight, BigDecimal trfCartedWeight, BigDecimal trfCartedPackages, String status,
			String createdBy, Date createdDate, String editedBy, Date editedDate, String approvedBy, Date approvedDate,
			Date gateInDate, BigDecimal gateInPackages, BigDecimal trfGateInPackages, BigDecimal gateInWeight,
			BigDecimal trfGateInWeight, String cha, String onAccountOf, BigDecimal grossWeight,
			BigDecimal actualNoOfPackages) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.finYear = finYear;
		this.sbChangeTransId = sbChangeTransId;
		this.transLineId = transLineId;
		this.cartingTransId = cartingTransId;
		this.cartingLineId = cartingLineId;
		this.sbTransId = sbTransId;
		this.vehicleNo = vehicleNo;
		this.gateInId = gateInId;
		this.sbNo = sbNo;
		this.sbLineNo = sbLineNo;
		this.trfSbTransId = trfSbTransId;
		this.trfSbNo = trfSbNo;
		this.trfSbLineNo = trfSbLineNo;
		this.cartingPackages = cartingPackages;
		this.outPkgs = outPkgs;
		this.balancePkgs = balancePkgs;
		this.nilPackages = nilPackages;
		this.cartedWeight = cartedWeight;
		this.trfCartedWeight = trfCartedWeight;
		this.trfCartedPackages = trfCartedPackages;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.editedBy = editedBy;
		this.editedDate = editedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.gateInDate = gateInDate;
		this.gateInPackages = gateInPackages;
		this.trfGateInPackages = trfGateInPackages;
		this.gateInWeight = gateInWeight;
		this.trfGateInWeight = trfGateInWeight;
		this.cha = cha;
		this.onAccountOf = onAccountOf;
		this.grossWeight = grossWeight;
		this.actualNoOfPackages = actualNoOfPackages;
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

	public String getSbChangeTransId() {
		return sbChangeTransId;
	}

	public void setSbChangeTransId(String sbChangeTransId) {
		this.sbChangeTransId = sbChangeTransId;
	}

	public String getTransLineId() {
		return transLineId;
	}

	public void setTransLineId(String transLineId) {
		this.transLineId = transLineId;
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

	public String getSbTransId() {
		return sbTransId;
	}

	public void setSbTransId(String sbTransId) {
		this.sbTransId = sbTransId;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getGateInId() {
		return gateInId;
	}

	public void setGateInId(String gateInId) {
		this.gateInId = gateInId;
	}

	public String getSbNo() {
		return sbNo;
	}

	public void setSbNo(String sbNo) {
		this.sbNo = sbNo;
	}

	public String getSbLineNo() {
		return sbLineNo;
	}

	public void setSbLineNo(String sbLineNo) {
		this.sbLineNo = sbLineNo;
	}

	public String getTrfSbTransId() {
		return trfSbTransId;
	}

	public void setTrfSbTransId(String trfSbTransId) {
		this.trfSbTransId = trfSbTransId;
	}

	public String getTrfSbNo() {
		return trfSbNo;
	}

	public void setTrfSbNo(String trfSbNo) {
		this.trfSbNo = trfSbNo;
	}

	public String getTrfSbLineNo() {
		return trfSbLineNo;
	}

	public void setTrfSbLineNo(String trfSbLineNo) {
		this.trfSbLineNo = trfSbLineNo;
	}

	public BigDecimal getCartingPackages() {
		return cartingPackages;
	}

	public void setCartingPackages(BigDecimal cartingPackages) {
		this.cartingPackages = cartingPackages;
	}

	public BigDecimal getOutPkgs() {
		return outPkgs;
	}

	public void setOutPkgs(BigDecimal outPkgs) {
		this.outPkgs = outPkgs;
	}

	public BigDecimal getBalancePkgs() {
		return balancePkgs;
	}

	public void setBalancePkgs(BigDecimal balancePkgs) {
		this.balancePkgs = balancePkgs;
	}

	public BigDecimal getNilPackages() {
		return nilPackages;
	}

	public void setNilPackages(BigDecimal nilPackages) {
		this.nilPackages = nilPackages;
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

	public BigDecimal getTrfCartedPackages() {
		return trfCartedPackages;
	}

	public void setTrfCartedPackages(BigDecimal trfCartedPackages) {
		this.trfCartedPackages = trfCartedPackages;
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

	public Date getGateInDate() {
		return gateInDate;
	}

	public void setGateInDate(Date gateInDate) {
		this.gateInDate = gateInDate;
	}

	@Override
	public String toString() {
		return "ExportTransferDetail [companyId=" + companyId + ", branchId=" + branchId + ", finYear=" + finYear
				+ ", sbChangeTransId=" + sbChangeTransId + ", transLineId=" + transLineId + ", cartingTransId="
				+ cartingTransId + ", cartingLineId=" + cartingLineId + ", sbTransId=" + sbTransId + ", vehicleNo="
				+ vehicleNo + ", gateInId=" + gateInId + ", sbNo=" + sbNo + ", sbLineNo=" + sbLineNo + ", trfSbTransId="
				+ trfSbTransId + ", trfSbNo=" + trfSbNo + ", trfSbLineNo=" + trfSbLineNo + ", cartingPackages="
				+ cartingPackages + ", outPkgs=" + outPkgs + ", balancePkgs=" + balancePkgs + ", nilPackages="
				+ nilPackages + ", cartedWeight=" + cartedWeight + ", trfCartedWeight=" + trfCartedWeight
				+ ", trfCartedPackages=" + trfCartedPackages + ", status=" + status + ", createdBy=" + createdBy
				+ ", createdDate=" + createdDate + ", editedBy=" + editedBy + ", editedDate=" + editedDate
				+ ", approvedBy=" + approvedBy + ", approvedDate=" + approvedDate + ", gateInDate=" + gateInDate
				+ ", gateInPackages=" + gateInPackages + ", trfGateInPackages=" + trfGateInPackages + ", gateInWeight="
				+ gateInWeight + ", trfGateInWeight=" + trfGateInWeight + "]";
	}

	
	
    
	
    
}
