package com.cwms.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name="cfimportgatepassvehdtl")
@IdClass(CfImportGatePassVehDtlId.class)
public class CfImportGatePassVehDtl {
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
    @Column(name = "Profitcentre_Id", length = 6)
    private String profitcentreId;

    @Id
    @Column(name = "Gate_Pass_Id", length = 10)
    private String gatePassId;

    @Id
    @Column(name = "SR_No")
    private int srNo;

    @Id
    @Column(name = "Container_No", length = 11)
    private String containerNo;

    @Id
    @Column(name = "IGM_No", length = 10)
    private String igmNo;

    @Id
    @Column(name = "IGM_LINE_NO", length = 10)
    private String igmLineNo;

    @Id
    @Column(name = "IGM_Trans_Id", length = 10)
    private String igmTransId;

    @Column(name = "CON_SR_NO", precision = 8, scale = 0)
    private BigDecimal conSrNo;

    @Column(name = "Trans_Type", length = 10)
    private String transType;

    @Column(name = "Gross_Wt", precision = 16, scale = 3)
    private BigDecimal grossWt;

    @Column(name = "No_Of_Package", precision = 16, scale = 3)
    private BigDecimal noOfPackage;

    @Column(name = "Vehicle_No", length = 15)
    private String vehicleNo;
    
    @Column(name = "Vehicle_GatePass_Id", length = 15)
    private String vehicleGatePassId;

    @Column(name = "QTY_TAKEN_OUT", precision = 16, scale = 3)
    private BigDecimal qtyTakenOut;

    @Column(name = "GW_Taken_Out", precision = 16, scale = 3)
    private BigDecimal gwTakenOut;

    @Column(name = "BE_QTY_TAKEN_OUT", precision = 16, scale = 3)
    private BigDecimal beQtyTakenOut;

    @Column(name = "BE_GW_Taken_Out", precision = 16, scale = 3)
    private BigDecimal beGwTakenOut;

    @Column(name = "Created_By", length = 10)
    private String createdBy;

    @Column(name = "Created_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name = "Status", length = 1)
    private char status;

    @Column(name = "remarks", length = 250)
    private String remarks;

	public CfImportGatePassVehDtl() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public CfImportGatePassVehDtl(String companyId, String branchId, String finYear, String profitcentreId,
			String gatePassId, int srNo, String containerNo, String igmNo, String igmLineNo, String igmTransId,
			BigDecimal conSrNo, String transType, BigDecimal grossWt, BigDecimal noOfPackage, String vehicleNo,
			String vehicleGatePassId, BigDecimal qtyTakenOut, BigDecimal gwTakenOut, BigDecimal beQtyTakenOut,
			BigDecimal beGwTakenOut, String createdBy, Date createdDate, char status, String remarks) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.finYear = finYear;
		this.profitcentreId = profitcentreId;
		this.gatePassId = gatePassId;
		this.srNo = srNo;
		this.containerNo = containerNo;
		this.igmNo = igmNo;
		this.igmLineNo = igmLineNo;
		this.igmTransId = igmTransId;
		this.conSrNo = conSrNo;
		this.transType = transType;
		this.grossWt = grossWt;
		this.noOfPackage = noOfPackage;
		this.vehicleNo = vehicleNo;
		this.vehicleGatePassId = vehicleGatePassId;
		this.qtyTakenOut = qtyTakenOut;
		this.gwTakenOut = gwTakenOut;
		this.beQtyTakenOut = beQtyTakenOut;
		this.beGwTakenOut = beGwTakenOut;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.status = status;
		this.remarks = remarks;
	}



	public String getVehicleGatePassId() {
		return vehicleGatePassId;
	}



	public void setVehicleGatePassId(String vehicleGatePassId) {
		this.vehicleGatePassId = vehicleGatePassId;
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

	public String getContainerNo() {
		return containerNo;
	}

	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
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

	public BigDecimal getConSrNo() {
		return conSrNo;
	}

	public void setConSrNo(BigDecimal conSrNo) {
		this.conSrNo = conSrNo;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
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

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public BigDecimal getQtyTakenOut() {
		return qtyTakenOut;
	}

	public void setQtyTakenOut(BigDecimal qtyTakenOut) {
		this.qtyTakenOut = qtyTakenOut;
	}

	public BigDecimal getGwTakenOut() {
		return gwTakenOut;
	}

	public void setGwTakenOut(BigDecimal gwTakenOut) {
		this.gwTakenOut = gwTakenOut;
	}

	public BigDecimal getBeQtyTakenOut() {
		return beQtyTakenOut;
	}

	public void setBeQtyTakenOut(BigDecimal beQtyTakenOut) {
		this.beQtyTakenOut = beQtyTakenOut;
	}

	public BigDecimal getBeGwTakenOut() {
		return beGwTakenOut;
	}

	public void setBeGwTakenOut(BigDecimal beGwTakenOut) {
		this.beGwTakenOut = beGwTakenOut;
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

	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
    
    
    
}
