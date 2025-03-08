package com.cwms.entities;

import java.math.BigDecimal;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.IdClass;
import java.util.Date;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "cfhubdocument")
@IdClass(HubDocumentId.class)
public class HubDocument {

    @Id
    @Column(name = "Fin_Year", length = 4, nullable = false)
    private String finYear;

    @Id
    @Column(name = "Company_Id", length = 6, nullable = false)
    private String companyId;

    @Id
    @Column(name = "Branch_Id", length = 6, nullable = false)
    private String branchId;

    @Id
    @Column(name = "HUB_Trans_Id", length = 10, nullable = false)
    private String hubTransId;

    @Id
    @Column(name = "IGM_Line_No", length = 20)
    private String igmLineNo = "";

    @Id
    @Column(name = "Igm_no", length = 10, nullable = false)
    private String igmNo;

    @Column(name = "HUB_Trans_date", nullable = false)
    private Date hubTransDate;

    @Column(name = "No_Of_Packages", length = 10)
    private Integer noOfPackages = 0;

    @Column(name = "Gross_Wt", precision = 10, scale = 3)
    private BigDecimal grossWt =  BigDecimal.ZERO ;

    @Column(name = "Cargo_Wt", precision = 10, scale = 3)
    private BigDecimal cargoWt  =  BigDecimal.ZERO ;

    @Column(name = "CARGO_DESCRIPTION", length = 300)
    private String cargoDescription;

    @Column(name = "Cargo_Type", length = 20, nullable = false)
    private String cargoType;

    @Column(name = "Importer_Name", length = 100, nullable = false)
    private String importerName;

    @Column(name = "Importer_Address", length = 250, nullable = false)
    private String importerAddress;

    @Column(name = "SA", length = 20)
    private String sa;

    @Column(name = "Area", length = 20)
    private String area = "0";

    @Column(name = "Status", length = 1, nullable = false)
    private char status;

    @Column(name = "Created_By", length = 10, nullable = false)
    private String createdBy;

    @Column(name = "Created_Date", nullable = false)
    private Date createdDate;

    @Column(name = "Edited_By", length = 10)
    private String editedBy;

    @Column(name = "Edited_Date")
    private Date editedDate;

    @Column(name = "Approved_By", length = 10)
    private String approvedBy;

    @Column(name = "Approved_Date")
    private Date approvedDate;

    @Column(name = "SL", length = 20)
    private String sl;

    @Column(name = "Gate_In_Packages", precision = 8, scale = 0)
    private BigDecimal gateInPackages =  BigDecimal.ZERO ;
    
    @Column(name = "GateIn__Wt", precision = 10, scale = 3)
    private BigDecimal gateInWt  =  BigDecimal.ZERO ;

    @Column(name = "Stuff_Req_Qty")
    private Integer stuffReqQty =  0 ;

    @Column(name = "Stuff_Req_Weight", precision = 16, scale = 3)
    private BigDecimal stuffReqWeight =  BigDecimal.ZERO ;

    @Column(name = "Profitcentre_Id", length = 6, nullable = false)
    private String profitCentreId;
    
    transient private String profitCentreName;
    transient private String shippingAgentName;
    transient private String shippingLineName;
    
    transient private Integer balanceQuantity;
    transient private BigDecimal balanceWeight;
    
    
    
    
	public Integer getBalanceQuantity() {
		return balanceQuantity;
	}


	public void setBalanceQuantity(Integer balanceQuantity) {
		this.balanceQuantity = balanceQuantity;
	}


	public BigDecimal getBalanceWeight() {
		return balanceWeight;
	}


	public void setBalanceWeight(BigDecimal balanceWeight) {
		this.balanceWeight = balanceWeight;
	}


	public BigDecimal getGateInWt() {
		return gateInWt;
	}


	public void setGateInWt(BigDecimal gateInWt) {
		this.gateInWt = gateInWt;
	}


	public HubDocument() {
		super();
		// TODO Auto-generated constructor stub
	}


	public HubDocument(String finYear, String companyId, String branchId, String hubTransId, String igmLineNo,
			String igmNo, Date hubTransDate, Integer noOfPackages, BigDecimal grossWt, BigDecimal cargoWt,
			String cargoDescription, String cargoType, String importerName, String importerAddress, String sa,
			String area, char status, String createdBy, Date createdDate, String editedBy, Date editedDate,
			String approvedBy, Date approvedDate, String sl, BigDecimal gateInPackages, Integer stuffReqQty,
			BigDecimal stuffReqWeight, String profitCentreId, String profitCentreName, String shippingAgentName,
			String shippingLineName) {
		super();
		this.finYear = finYear;
		this.companyId = companyId;
		this.branchId = branchId;
		this.hubTransId = hubTransId;
		this.igmLineNo = igmLineNo;
		this.igmNo = igmNo;
		this.hubTransDate = hubTransDate;
		this.noOfPackages = noOfPackages;
		this.grossWt = grossWt;
		this.cargoWt = cargoWt;
		this.cargoDescription = cargoDescription;
		this.cargoType = cargoType;
		this.importerName = importerName;
		this.importerAddress = importerAddress;
		this.sa = sa;
		this.area = area;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.editedBy = editedBy;
		this.editedDate = editedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.sl = sl;
		this.gateInPackages = gateInPackages;
		this.stuffReqQty = stuffReqQty;
		this.stuffReqWeight = stuffReqWeight;
		this.profitCentreId = profitCentreId;
		this.profitCentreName = profitCentreName;
		this.shippingAgentName = shippingAgentName;
		this.shippingLineName = shippingLineName;
	}


	public String getFinYear() {
		return finYear;
	}


	public void setFinYear(String finYear) {
		this.finYear = finYear;
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


	public String getHubTransId() {
		return hubTransId;
	}


	public void setHubTransId(String hubTransId) {
		this.hubTransId = hubTransId;
	}


	public String getIgmLineNo() {
		return igmLineNo;
	}


	public void setIgmLineNo(String igmLineNo) {
		this.igmLineNo = igmLineNo;
	}


	public String getIgmNo() {
		return igmNo;
	}


	public void setIgmNo(String igmNo) {
		this.igmNo = igmNo;
	}


	public Date getHubTransDate() {
		return hubTransDate;
	}


	public void setHubTransDate(Date hubTransDate) {
		this.hubTransDate = hubTransDate;
	}


	public Integer getNoOfPackages() {
		return noOfPackages;
	}


	public void setNoOfPackages(Integer noOfPackages) {
		this.noOfPackages = noOfPackages;
	}


	public BigDecimal getGrossWt() {
		return grossWt;
	}


	public void setGrossWt(BigDecimal grossWt) {
		this.grossWt = grossWt;
	}


	public BigDecimal getCargoWt() {
		return cargoWt;
	}


	public void setCargoWt(BigDecimal cargoWt) {
		this.cargoWt = cargoWt;
	}


	public String getCargoDescription() {
		return cargoDescription;
	}


	public void setCargoDescription(String cargoDescription) {
		this.cargoDescription = cargoDescription;
	}


	public String getCargoType() {
		return cargoType;
	}


	public void setCargoType(String cargoType) {
		this.cargoType = cargoType;
	}


	public String getImporterName() {
		return importerName;
	}


	public void setImporterName(String importerName) {
		this.importerName = importerName;
	}


	public String getImporterAddress() {
		return importerAddress;
	}


	public void setImporterAddress(String importerAddress) {
		this.importerAddress = importerAddress;
	}


	public String getSa() {
		return sa;
	}


	public void setSa(String sa) {
		this.sa = sa;
	}


	public String getArea() {
		return area;
	}


	public void setArea(String area) {
		this.area = area;
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


	public String getSl() {
		return sl;
	}


	public void setSl(String sl) {
		this.sl = sl;
	}


	public BigDecimal getGateInPackages() {
		return gateInPackages;
	}


	public void setGateInPackages(BigDecimal gateInPackages) {
		this.gateInPackages = gateInPackages;
	}


	public Integer getStuffReqQty() {
		return stuffReqQty;
	}


	public void setStuffReqQty(Integer stuffReqQty) {
		this.stuffReqQty = stuffReqQty;
	}


	public BigDecimal getStuffReqWeight() {
		return stuffReqWeight;
	}


	public void setStuffReqWeight(BigDecimal stuffReqWeight) {
		this.stuffReqWeight = stuffReqWeight;
	}


	public String getProfitCentreId() {
		return profitCentreId;
	}


	public void setProfitCentreId(String profitCentreId) {
		this.profitCentreId = profitCentreId;
	}


	public String getProfitCentreName() {
		return profitCentreName;
	}


	public void setProfitCentreName(String profitCentreName) {
		this.profitCentreName = profitCentreName;
	}


	public String getShippingAgentName() {
		return shippingAgentName;
	}


	public void setShippingAgentName(String shippingAgentName) {
		this.shippingAgentName = shippingAgentName;
	}


	public String getShippingLineName() {
		return shippingLineName;
	}


	@Override
	public String toString() {
		return "HubDocument [finYear=" + finYear + ", companyId=" + companyId + ", branchId=" + branchId
				+ ", hubTransId=" + hubTransId + ", igmLineNo=" + igmLineNo + ", igmNo=" + igmNo + ", hubTransDate="
				+ hubTransDate + ", noOfPackages=" + noOfPackages + ", grossWt=" + grossWt + ", cargoWt=" + cargoWt
				+ ", cargoDescription=" + cargoDescription + ", cargoType=" + cargoType + ", importerName="
				+ importerName + ", importerAddress=" + importerAddress + ", sa=" + sa + ", area=" + area + ", status="
				+ status + ", createdBy=" + createdBy + ", createdDate=" + createdDate + ", editedBy=" + editedBy
				+ ", editedDate=" + editedDate + ", approvedBy=" + approvedBy + ", approvedDate=" + approvedDate
				+ ", sl=" + sl + ", gateInPackages=" + gateInPackages + ", stuffReqQty=" + stuffReqQty
				+ ", stuffReqWeight=" + stuffReqWeight + ", profitCentreId=" + profitCentreId + "]";
	}


	public void setShippingLineName(String shippingLineName) {
		this.shippingLineName = shippingLineName;
	}


	public HubDocument(String companyId, String branchId, String hubTransId, String igmLineNo, String igmNo,
			Date hubTransDate, Integer noOfPackages, BigDecimal grossWt, BigDecimal cargoWt, String cargoDescription,
			String cargoType, String importerName, String importerAddress, String sa, String area, char status,
			String createdBy, String approvedBy, String sl, String profitCentreId, String profitCentreName,
			String shippingAgentName, String shippingLineName) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.hubTransId = hubTransId;
		this.igmLineNo = igmLineNo;
		this.igmNo = igmNo;
		this.hubTransDate = hubTransDate;
		this.noOfPackages = noOfPackages;
		this.grossWt = grossWt;
		this.cargoWt = cargoWt;
		this.cargoDescription = cargoDescription;
		this.cargoType = cargoType;
		this.importerName = importerName;
		this.importerAddress = importerAddress;
		this.sa = sa;
		this.area = area;
		this.status = status;
		this.createdBy = createdBy;
		this.approvedBy = approvedBy;
		this.sl = sl;
		this.profitCentreId = profitCentreId;
		this.profitCentreName = profitCentreName;
		this.shippingAgentName = shippingAgentName;
		this.shippingLineName = shippingLineName;
	}
    
    
    
	
	
	public HubDocument(String companyId, String branchId, String hubTransId, String igmNo,
			Date hubTransDate, Integer noOfPackages, BigDecimal grossWt, BigDecimal cargoWt, String cargoDescription,
			String cargoType, String importerName, String area, char status,			
			String shippingAgentName, String profitCentreId) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.hubTransId = hubTransId;
		this.igmNo = igmNo;
		this.hubTransDate = hubTransDate;
		this.noOfPackages = noOfPackages;
		this.grossWt = grossWt;
		this.cargoWt = cargoWt;
		this.cargoDescription = cargoDescription;
		this.cargoType = cargoType;
		this.importerName = importerName;
		this.area = area;
		this.status = status;	
		this.shippingAgentName = shippingAgentName;
		this.profitCentreId = profitCentreId;
	}
	
    
    
}