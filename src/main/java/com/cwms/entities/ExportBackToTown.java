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
@Table(name = "cfbacktotown")
@IdClass(ExportBackToTownId.class)
public class ExportBackToTown implements Cloneable {
	@Id
	@Column(name = "Company_Id", length = 6)
	private String companyId;

	@Id
	@Column(name = "Branch_Id", length = 6)
	private String branchId;

	@Id
	@Column(name = "Back_To_Town_Trans_Id", length = 10)
	private String backToTownTransId;

	@Id
	@Column(name = "Back_To_Town_Line_Id", length = 4)
	private String backToTownLineId;

	@Id
	@Column(name = "Profitcentre_Id", length = 6)
	private String profitcentreId;

	@Column(name = "Back_To_Town_Trans_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date backToTownTransDate;

	@Id
	@Column(name = "SB_Trans_Id", length = 10)
	private String sbTransId;

	@Id
	@Column(name = "SB_Line_No", length = 5)
	private String sbLineNo;

	@Id
	@Column(name = "SB_No", length = 15)
	private String sbNo;

	@Column(name = "SB_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date sbDate;
	
	@Column(name = "SB_Trans_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date sbTransDate;

	@Column(name = "Number_Of_Marks")
	private String numberOfMarks;

	@Column(name = "imp_Sr_no")
	private int impSrNo;

	@Column(name = "Importer_Id", length = 7)
	private String importerId;

	@Column(name = "billing_party", length = 3)
	private String billingParty;

	@Column(name = "IGST", length = 1)
	private String igst;

	@Column(name = "CGST", length = 1)
	private String cgst;

	@Column(name = "SGST", length = 1)
	private String sgst;

	@Column(name = "Acc_Sr_no")
	private int accSrNo;

	@Column(name = "On_Account_Of", length = 6)
	private String onAccountOf;

	@Column(name = "Request_Reference_No", length = 25)
	private String requestReferenceNo;

	@Column(name = "Request_Reference_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date requestReferenceDate;

	@Column(name = "Actual_No_Of_Packages", precision = 8, scale = 0)
	private BigDecimal actualNoOfPackages;

	@Column(name = "Back_To_Town_Packages", precision = 8, scale = 0)
	private BigDecimal backToTownPackages;

	@Column(name = "Gate_Out_Packages", precision = 8, scale = 0)
	private BigDecimal gateOutPackages;

	@Column(name = "Balance_Packages", precision = 8, scale = 0)
	private BigDecimal balancePackages;

	@Column(name = "Gross_Weight", precision = 16, scale = 3)
	private BigDecimal grossWeight;

	@Column(name = "Back_To_Town_Weight", precision = 16, scale = 3)
	private BigDecimal backToTownWeight;

	@Column(name = "Transporter_Status", length = 1)
	private String transporterStatus;

	@Column(name = "Transporter", length = 6)
	private String transporter;

	@Column(name = "Driver_Name", length = 50)
	private String driverName;

	@Column(name = "Transporter_Name", length = 35)
	private String transporterName;

	@Column(name = "Vehicle_No", length = 10)
	private String vehicleNo;

	@Column(name = "Vehicle_Status", length = 1)
	private String vehicleStatus;

	@Column(name = "Gate_Out_Id", length = 10)
	private String gateOutId;

	@Column(name = "Gate_Out_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date gateOutDate;

	@Column(name = "Commodity", length = 250)
	private String commodity;

	@Column(name = "Comments", length = 100)
	private String comments;

	@Column(name = "Agro_Product_Flag", length = 1)
	private String agroProductFlag;

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

	@Column(name = "Invoice_No", length = 20)
	private String invoiceNo;

	@Column(name = "Assesment_Id", length = 20)
	private String assesmentId;

	@Column(name = "Invoice_Assesed", length = 1)
	private String invoiceAssessed;

	@Column(name = "oth_party_Id", length = 6)
	private String othPartyId;

	@Column(name = "oth_Sr_no", precision = 8, scale = 0)
	private BigDecimal othSrNo;

	@Column(name = "Invoice_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date invoiceDate;

	@Column(name = "Credit_Type", length = 1)
	private String creditType;

	@Column(name = "Invoice_Category", length = 10)
	private String invoiceCategory;

	@Column(name = "Bill_Amt", precision = 16, scale = 3)
	private BigDecimal billAmt;

	@Column(name = "Invoice_Amt", precision = 16, scale = 3)
	private BigDecimal invoiceAmt;

	@Column(name = "Type_of_Container", length = 10)
	private String typeOfContainer;

	@Column(name = "SSR_Trans_Id", length = 10)
	private String ssrTransId;
	
	@Column(name = "Gate_Pass_Id", length = 10)
	private String gatePassId;
	
	
	@Transient
	private String exporterAddress;
	
	@Transient
	private String onAccountOfName;

	public ExportBackToTown() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	

	public String getGatePassId() {
		return gatePassId;
	}




	public void setGatePassId(String gatePassId) {
		this.gatePassId = gatePassId;
	}




	public ExportBackToTown(String companyId, String branchId, String backToTownTransId, String backToTownLineId,
			String profitcentreId, Date backToTownTransDate, String sbTransId, String sbLineNo, String sbNo,
			Date sbDate, Date sbTransDate, String numberOfMarks, int impSrNo, String importerId, String billingParty,
			String igst, String cgst, String sgst, int accSrNo, String onAccountOf, String requestReferenceNo,
			Date requestReferenceDate, BigDecimal actualNoOfPackages, BigDecimal backToTownPackages,
			BigDecimal gateOutPackages, BigDecimal balancePackages, BigDecimal grossWeight, BigDecimal backToTownWeight,
			String transporterStatus, String transporter, String driverName, String transporterName, String vehicleNo,
			String vehicleStatus, String gateOutId, Date gateOutDate, String commodity, String comments,
			String agroProductFlag, String status, String createdBy, Date createdDate, String editedBy, Date editedDate,
			String approvedBy, Date approvedDate, String invoiceNo, String assesmentId, String invoiceAssessed,
			String othPartyId, BigDecimal othSrNo, Date invoiceDate, String creditType, String invoiceCategory,
			BigDecimal billAmt, BigDecimal invoiceAmt, String typeOfContainer, String ssrTransId, String gatePassId,
			String exporterAddress, String onAccountOfName) {
		this.companyId = companyId;
		this.branchId = branchId;
		this.backToTownTransId = backToTownTransId;
		this.backToTownLineId = backToTownLineId;
		this.profitcentreId = profitcentreId;
		this.backToTownTransDate = backToTownTransDate;
		this.sbTransId = sbTransId;
		this.sbLineNo = sbLineNo;
		this.sbNo = sbNo;
		this.sbDate = sbDate;
		this.sbTransDate = sbTransDate;
		this.numberOfMarks = numberOfMarks;
		this.impSrNo = impSrNo;
		this.importerId = importerId;
		this.billingParty = billingParty;
		this.igst = igst;
		this.cgst = cgst;
		this.sgst = sgst;
		this.accSrNo = accSrNo;
		this.onAccountOf = onAccountOf;
		this.requestReferenceNo = requestReferenceNo;
		this.requestReferenceDate = requestReferenceDate;
		this.actualNoOfPackages = actualNoOfPackages;
		this.backToTownPackages = backToTownPackages;
		this.gateOutPackages = gateOutPackages;
		this.balancePackages = balancePackages;
		this.grossWeight = grossWeight;
		this.backToTownWeight = backToTownWeight;
		this.transporterStatus = transporterStatus;
		this.transporter = transporter;
		this.driverName = driverName;
		this.transporterName = transporterName;
		this.vehicleNo = vehicleNo;
		this.vehicleStatus = vehicleStatus;
		this.gateOutId = gateOutId;
		this.gateOutDate = gateOutDate;
		this.commodity = commodity;
		this.comments = comments;
		this.agroProductFlag = agroProductFlag;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.editedBy = editedBy;
		this.editedDate = editedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.invoiceNo = invoiceNo;
		this.assesmentId = assesmentId;
		this.invoiceAssessed = invoiceAssessed;
		this.othPartyId = othPartyId;
		this.othSrNo = othSrNo;
		this.invoiceDate = invoiceDate;
		this.creditType = creditType;
		this.invoiceCategory = invoiceCategory;
		this.billAmt = billAmt;
		this.invoiceAmt = invoiceAmt;
		this.typeOfContainer = typeOfContainer;
		this.ssrTransId = ssrTransId;
		this.gatePassId = gatePassId;
		this.exporterAddress = exporterAddress;
		this.onAccountOfName = onAccountOfName;
	}
















	public String getOnAccountOfName() {
		return onAccountOfName;
	}










	public void setOnAccountOfName(String onAccountOfName) {
		this.onAccountOfName = onAccountOfName;
	}










	public String getExporterAddress() {
		return exporterAddress;
	}





	public void setExporterAddress(String exporterAddress) {
		this.exporterAddress = exporterAddress;
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

	public String getBackToTownTransId() {
		return backToTownTransId;
	}

	public void setBackToTownTransId(String backToTownTransId) {
		this.backToTownTransId = backToTownTransId;
	}

	public String getBackToTownLineId() {
		return backToTownLineId;
	}

	public void setBackToTownLineId(String backToTownLineId) {
		this.backToTownLineId = backToTownLineId;
	}

	public String getProfitcentreId() {
		return profitcentreId;
	}

	public void setProfitcentreId(String profitcentreId) {
		this.profitcentreId = profitcentreId;
	}

	public Date getBackToTownTransDate() {
		return backToTownTransDate;
	}

	public void setBackToTownTransDate(Date backToTownTransDate) {
		this.backToTownTransDate = backToTownTransDate;
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

	public String getNumberOfMarks() {
		return numberOfMarks;
	}

	public void setNumberOfMarks(String numberOfMarks) {
		this.numberOfMarks = numberOfMarks;
	}

	public int getImpSrNo() {
		return impSrNo;
	}

	public void setImpSrNo(int impSrNo) {
		this.impSrNo = impSrNo;
	}

	public String getImporterId() {
		return importerId;
	}

	public void setImporterId(String importerId) {
		this.importerId = importerId;
	}

	public String getBillingParty() {
		return billingParty;
	}

	public void setBillingParty(String billingParty) {
		this.billingParty = billingParty;
	}

	public String getIgst() {
		return igst;
	}

	public void setIgst(String igst) {
		this.igst = igst;
	}

	public String getCgst() {
		return cgst;
	}

	public void setCgst(String cgst) {
		this.cgst = cgst;
	}

	public String getSgst() {
		return sgst;
	}

	public void setSgst(String sgst) {
		this.sgst = sgst;
	}

	public int getAccSrNo() {
		return accSrNo;
	}

	public void setAccSrNo(int accSrNo) {
		this.accSrNo = accSrNo;
	}

	public String getOnAccountOf() {
		return onAccountOf;
	}

	public void setOnAccountOf(String onAccountOf) {
		this.onAccountOf = onAccountOf;
	}

	public String getRequestReferenceNo() {
		return requestReferenceNo;
	}

	public void setRequestReferenceNo(String requestReferenceNo) {
		this.requestReferenceNo = requestReferenceNo;
	}

	public Date getRequestReferenceDate() {
		return requestReferenceDate;
	}

	public void setRequestReferenceDate(Date requestReferenceDate) {
		this.requestReferenceDate = requestReferenceDate;
	}

	public BigDecimal getActualNoOfPackages() {
		return actualNoOfPackages;
	}

	public void setActualNoOfPackages(BigDecimal actualNoOfPackages) {
		this.actualNoOfPackages = actualNoOfPackages;
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

	public BigDecimal getBalancePackages() {
		return balancePackages;
	}

	public void setBalancePackages(BigDecimal balancePackages) {
		this.balancePackages = balancePackages;
	}

	public BigDecimal getGrossWeight() {
		return grossWeight;
	}

	public void setGrossWeight(BigDecimal grossWeight) {
		this.grossWeight = grossWeight;
	}

	public BigDecimal getBackToTownWeight() {
		return backToTownWeight;
	}

	public void setBackToTownWeight(BigDecimal backToTownWeight) {
		this.backToTownWeight = backToTownWeight;
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

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getVehicleStatus() {
		return vehicleStatus;
	}

	public void setVehicleStatus(String vehicleStatus) {
		this.vehicleStatus = vehicleStatus;
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

	public String getCommodity() {
		return commodity;
	}

	public void setCommodity(String commodity) {
		this.commodity = commodity;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getAgroProductFlag() {
		return agroProductFlag;
	}

	public void setAgroProductFlag(String agroProductFlag) {
		this.agroProductFlag = agroProductFlag;
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

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getAssesmentId() {
		return assesmentId;
	}

	public void setAssesmentId(String assesmentId) {
		this.assesmentId = assesmentId;
	}

	public String getInvoiceAssessed() {
		return invoiceAssessed;
	}

	public void setInvoiceAssessed(String invoiceAssessed) {
		this.invoiceAssessed = invoiceAssessed;
	}

	public String getOthPartyId() {
		return othPartyId;
	}

	public void setOthPartyId(String othPartyId) {
		this.othPartyId = othPartyId;
	}

	public BigDecimal getOthSrNo() {
		return othSrNo;
	}

	public void setOthSrNo(BigDecimal othSrNo) {
		this.othSrNo = othSrNo;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getCreditType() {
		return creditType;
	}

	public void setCreditType(String creditType) {
		this.creditType = creditType;
	}

	public String getInvoiceCategory() {
		return invoiceCategory;
	}

	public void setInvoiceCategory(String invoiceCategory) {
		this.invoiceCategory = invoiceCategory;
	}

	public BigDecimal getBillAmt() {
		return billAmt;
	}

	public void setBillAmt(BigDecimal billAmt) {
		this.billAmt = billAmt;
	}

	public BigDecimal getInvoiceAmt() {
		return invoiceAmt;
	}

	public void setInvoiceAmt(BigDecimal invoiceAmt) {
		this.invoiceAmt = invoiceAmt;
	}

	public String getTypeOfContainer() {
		return typeOfContainer;
	}

	public void setTypeOfContainer(String typeOfContainer) {
		this.typeOfContainer = typeOfContainer;
	}

	public String getSsrTransId() {
		return ssrTransId;
	}

	public void setSsrTransId(String ssrTransId) {
		this.ssrTransId = ssrTransId;
	}


	public Date getSbTransDate() {
		return sbTransDate;
	}


	public void setSbTransDate(Date sbTransDate) {
		this.sbTransDate = sbTransDate;
	}
	
	
	
	
	
	
	public ExportBackToTown(String backToTownTransId, String backToTownLineId, Date backToTownTransDate,
			String sbTransId, String sbNo, Date sbDate, Date sbTransDate, String numberOfMarks, String importerId,
			String onAccountOf, String requestReferenceNo, Date requestReferenceDate, BigDecimal actualNoOfPackages,
			BigDecimal backToTownPackages, BigDecimal balancePackages, BigDecimal grossWeight,
			BigDecimal backToTownWeight, String commodity, String status, String createdBy, String typeOfContainer,
			String exporterAddress, String onAccountOfName) {
		this.backToTownTransId = backToTownTransId;
		this.backToTownLineId = backToTownLineId;
		this.backToTownTransDate = backToTownTransDate;
		this.sbTransId = sbTransId;
		this.sbNo = sbNo;
		this.sbDate = sbDate;
		this.sbTransDate = sbTransDate;
		this.numberOfMarks = numberOfMarks;
		this.importerId = importerId;
		this.onAccountOf = onAccountOf;
		this.requestReferenceNo = requestReferenceNo;
		this.requestReferenceDate = requestReferenceDate;
		this.actualNoOfPackages = actualNoOfPackages;
		this.backToTownPackages = backToTownPackages;
		this.balancePackages = balancePackages;
		this.grossWeight = grossWeight;
		this.backToTownWeight = backToTownWeight;
		this.commodity = commodity;
		this.status = status;
		this.createdBy = createdBy;
		this.typeOfContainer = typeOfContainer;
		this.exporterAddress = exporterAddress;
		this.onAccountOfName = onAccountOfName;
	}



	







	public ExportBackToTown(String backToTownTransId, String sbTransId, String sbNo, Date sbDate, String importerId,
			BigDecimal backToTownPackages, String commodity) {
		this.backToTownTransId = backToTownTransId;
		this.sbTransId = sbTransId;
		this.sbNo = sbNo;
		this.sbDate = sbDate;
		this.importerId = importerId;
		this.backToTownPackages = backToTownPackages;
		this.commodity = commodity;
	}




	@Override
	public Object clone() throws CloneNotSupportedException {
	    return super.clone();
	}
	
}
