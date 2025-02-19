package com.cwms.entities;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;


@Entity
@Table(name = "cfsbcrg")
@IdClass(ExportSbCargoEntryId.class)
public class ExportSbCargoEntry {
	
	@Id   
    private Long srno;

    @Id
    @Column(name = "Company_Id", nullable = false, length = 6)
    private String companyId;

    @Id
    @Column(name = "Branch_Id", nullable = false, length = 6)
    private String branchId;

    @Id
    @Column(name = "Fin_Year", nullable = false, length = 4)
    private String finYear;

    @Id
    @Column(name = "Profitcentre_Id", nullable = false, length = 6)
    private String profitcentreId;

    @Id
    @Column(name = "SB_Trans_Id", nullable = false, length = 10)
    private String sbTransId;
    
    @Id
    @Column(name = "SB_Line_No", nullable = false, length = 4)
    private String sbLineNo;    
    
    @Id
    @Column(name = "SB_No", nullable = false, length = 10)
    private String sbNo;
    
    @Column(name = "Commodity", length = 250)
    private String commodity;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @Column(name = "SB_Date", nullable = false)
    private Date sbDate;

    @Column(name = "Number_Of_Marks")
    private String numberOfMarks;

    @Column(name = "No_Of_Packages", nullable = false, precision = 8, scale = 0)
    private BigDecimal noOfPackages = BigDecimal.ZERO;

    @Column(name = "Gate_In_Packages", precision = 8, scale = 0)
    private BigDecimal gateInPackages = BigDecimal.ZERO;

    @Column(name = "Carted_Packages", precision = 8, scale = 0)
    private BigDecimal cartedPackages = BigDecimal.ZERO;

    @Column(name = "Stuff_Req_Qty", precision = 10, scale = 0)
    private Integer stuffReqQty = 0;

    @Column(name = "Stuffed_Qty", precision = 8, scale = 0)
    private BigDecimal stuffedQty = BigDecimal.ZERO;

    @Column(name = "Back_To_Town_Pack", precision = 10, scale = 0)
    private Integer backToTownPack = 0;

    @Column(name = "BTT_Out_Qty", nullable = false, precision = 8, scale = 0)
    private BigDecimal bttOutQty = BigDecimal.ZERO;

    @Column(name = "Transfer_Packages", nullable = false, precision = 8, scale = 0)
    private BigDecimal transferPackages = BigDecimal.ZERO;

    @Column(name = "Nil_Packages", nullable = false, precision = 8, scale = 0)
    private BigDecimal nilPackages = BigDecimal.ZERO;

    @Column(name = "Type_Of_Package", nullable = false, length = 6)
    private String typeOfPackage;

    @Column(name = "Gross_Weight", nullable = false, precision = 16, scale = 4)
    private BigDecimal grossWeight = BigDecimal.valueOf(0.0000);

    @Column(name = "FOB", nullable = false, precision = 16, scale = 4)
    private BigDecimal fob = BigDecimal.valueOf(0.0000);

    @Column(name = "Created_By", length = 10)
    private String createdBy;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @Column(name = "Created_Date", nullable = false)
    private Date createdDate;

    @Column(name = "Approved_By", length = 10)
    private String approvedBy;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @Column(name = "Approved_Date", nullable = false)
    private Date approvedDate;

    @Column(name = "Status", length = 100)
    private String status;

    @Column(name = "H_sb_trans_id", length = 10)
    private String hSbTransId;

    @Column(name = "sb_type", nullable = false, length = 20)
    private String sbType = "Normal";

    @Column(name = "Cargo_Type", length = 30)
    private String cargoType = "NAGRO";

    @Column(name = "Draw_Back_Value", precision = 12, scale = 4)
    private BigDecimal drawBackValue = BigDecimal.valueOf(0.0000);

    @Column(name = "Invoice_No", length = 16)
    private String invoiceNo;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @Column(name = "Invoice_Date")
    private Date invoiceDate;
    
    @Column(name = "Haz_Status", length = 1, columnDefinition = "char(1) DEFAULT ''")
    private String hazStatus;

    @Column(name = "Haz", length = 20)
    private String haz;

    @Column(name = "UN_no", length = 6)
    private String unNo;

    @Column(name = "New_Commodity", nullable = false, length = 50)
    private String newCommodity;

    @Column(name = "Invoice_Assesed", length = 1)
    private String invoiceAssesed;

    @Column(name = "Assesment_Id", length = 10)
    private String assesmentId;

    @Column(name = "CRGInvoice_No", length = 17)
    private String crgInvoiceNo;

    @Column(name = "Carting_Type", nullable = false, length = 6)
    private String cartingType;

    @Column(name = "IsBos", nullable = false, length = 1)
    private String isBos = "N";

    @Column(name = "zero_carting_flag", length = 1)
    private String zeroCartingFlag = "N";

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @Column(name = "zero_carting_Date")
    private Date zeroCartingDate;

    @Column(name = "zero_carting_approval", length = 50)
    private String zeroCartingApproval;

    @Column(name = "zero_remarks", length = 150)
    private String zeroRemarks;

    @Column(name = "BufferStuffing", nullable = false, length = 1)
    private String bufferStuffing = "N";

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @Column(name = "Last_Storage_Invoice_Date")
    private Date lastStorageInvoiceDate;

    @Column(name = "Last_Storage_Invoice_Done", nullable = false, length = 1)
    private String lastStorageInvoiceDone = "N";

    @Column(name = "Storage_Invoice_Assesed", nullable = false, length = 1)
    private String storageInvoiceAssesed = "N";

    @Column(name = "Storage_Assesment_Id", length = 10)
    private String storageAssesmentId;

    @Column(name = "SCMTR_Charge", nullable = false, length = 1)
    private String scmtrCharge = "N";

    @Column(name="Stuffed_Wt",precision = 16,scale = 3)
    private BigDecimal stuffedWt;
    
    
    
    
    
    
		public BigDecimal getStuffedWt() {
		return stuffedWt;
	}


	public void setStuffedWt(BigDecimal stuffedWt) {
		this.stuffedWt = stuffedWt;
	}


		public String getHazStatus() {
		return hazStatus;
	}


	public void setHazStatus(String hazStatus) {
		this.hazStatus = hazStatus;
	}


		public ExportSbCargoEntry() {
			super();
			// TODO Auto-generated constructor stub
		}
		
		

		
		public ExportSbCargoEntry(Long srno, String companyId, String branchId, String finYear, String profitcentreId,
				String sbTransId, String sbLineNo, String sbNo, String commodity, Date sbDate, String numberOfMarks,
				BigDecimal noOfPackages, BigDecimal gateInPackages, BigDecimal cartedPackages, Integer stuffReqQty,
				BigDecimal stuffedQty, Integer backToTownPack, BigDecimal bttOutQty, BigDecimal transferPackages,
				BigDecimal nilPackages, String typeOfPackage, BigDecimal grossWeight, BigDecimal fob, String createdBy,
				Date createdDate, String approvedBy, Date approvedDate, String status, String hSbTransId, String sbType,
				String cargoType, BigDecimal drawBackValue, String invoiceNo, Date invoiceDate, String hazStatus,
				String haz, String unNo, String newCommodity, String invoiceAssesed, String assesmentId,
				String crgInvoiceNo, String cartingType, String isBos, String zeroCartingFlag, Date zeroCartingDate,
				String zeroCartingApproval, String zeroRemarks, String bufferStuffing, Date lastStorageInvoiceDate,
				String lastStorageInvoiceDone, String storageInvoiceAssesed, String storageAssesmentId,
				String scmtrCharge, BigDecimal stuffedWt) {
			this.srno = srno;
			this.companyId = companyId;
			this.branchId = branchId;
			this.finYear = finYear;
			this.profitcentreId = profitcentreId;
			this.sbTransId = sbTransId;
			this.sbLineNo = sbLineNo;
			this.sbNo = sbNo;
			this.commodity = commodity;
			this.sbDate = sbDate;
			this.numberOfMarks = numberOfMarks;
			this.noOfPackages = noOfPackages;
			this.gateInPackages = gateInPackages;
			this.cartedPackages = cartedPackages;
			this.stuffReqQty = stuffReqQty;
			this.stuffedQty = stuffedQty;
			this.backToTownPack = backToTownPack;
			this.bttOutQty = bttOutQty;
			this.transferPackages = transferPackages;
			this.nilPackages = nilPackages;
			this.typeOfPackage = typeOfPackage;
			this.grossWeight = grossWeight;
			this.fob = fob;
			this.createdBy = createdBy;
			this.createdDate = createdDate;
			this.approvedBy = approvedBy;
			this.approvedDate = approvedDate;
			this.status = status;
			this.hSbTransId = hSbTransId;
			this.sbType = sbType;
			this.cargoType = cargoType;
			this.drawBackValue = drawBackValue;
			this.invoiceNo = invoiceNo;
			this.invoiceDate = invoiceDate;
			this.hazStatus = hazStatus;
			this.haz = haz;
			this.unNo = unNo;
			this.newCommodity = newCommodity;
			this.invoiceAssesed = invoiceAssesed;
			this.assesmentId = assesmentId;
			this.crgInvoiceNo = crgInvoiceNo;
			this.cartingType = cartingType;
			this.isBos = isBos;
			this.zeroCartingFlag = zeroCartingFlag;
			this.zeroCartingDate = zeroCartingDate;
			this.zeroCartingApproval = zeroCartingApproval;
			this.zeroRemarks = zeroRemarks;
			this.bufferStuffing = bufferStuffing;
			this.lastStorageInvoiceDate = lastStorageInvoiceDate;
			this.lastStorageInvoiceDone = lastStorageInvoiceDone;
			this.storageInvoiceAssesed = storageInvoiceAssesed;
			this.storageAssesmentId = storageAssesmentId;
			this.scmtrCharge = scmtrCharge;
			this.stuffedWt = stuffedWt;
		}


		public ExportSbCargoEntry(String companyId, String branchId, String finYear, String profitcentreId,
				String sbTransId, String sbLineNo, String sbNo, String commodity, Date sbDate, String numberOfMarks,
				BigDecimal noOfPackages, BigDecimal gateInPackages, BigDecimal cartedPackages, Integer stuffReqQty,
				BigDecimal stuffedQty, Integer backToTownPack, BigDecimal bttOutQty, BigDecimal transferPackages,
				BigDecimal nilPackages, String typeOfPackage, BigDecimal grossWeight, BigDecimal fob, String createdBy,
				Date createdDate, String approvedBy, Date approvedDate, String status, String hSbTransId, String sbType,
				String cargoType, BigDecimal drawBackValue, String invoiceNo, Date invoiceDate, String haz, String unNo,
				String newCommodity, String invoiceAssesed, String assesmentId, String crgInvoiceNo, String cartingType,
				String isBos, String zeroCartingFlag, Date zeroCartingDate, String zeroCartingApproval,
				String zeroRemarks, String bufferStuffing, Date lastStorageInvoiceDate, String lastStorageInvoiceDone,
				String storageInvoiceAssesed, String storageAssesmentId, String scmtrCharge) {
			super();
			this.companyId = companyId;
			this.branchId = branchId;
			this.finYear = finYear;
			this.profitcentreId = profitcentreId;
			this.sbTransId = sbTransId;
			this.sbLineNo = sbLineNo;
			this.sbNo = sbNo;
			this.commodity = commodity;
			this.sbDate = sbDate;
			this.numberOfMarks = numberOfMarks;
			this.noOfPackages = noOfPackages;
			this.gateInPackages = gateInPackages;
			this.cartedPackages = cartedPackages;
			this.stuffReqQty = stuffReqQty;
			this.stuffedQty = stuffedQty;
			this.backToTownPack = backToTownPack;
			this.bttOutQty = bttOutQty;
			this.transferPackages = transferPackages;
			this.nilPackages = nilPackages;
			this.typeOfPackage = typeOfPackage;
			this.grossWeight = grossWeight;
			this.fob = fob;
			this.createdBy = createdBy;
			this.createdDate = createdDate;
			this.approvedBy = approvedBy;
			this.approvedDate = approvedDate;
			this.status = status;
			this.hSbTransId = hSbTransId;
			this.sbType = sbType;
			this.cargoType = cargoType;
			this.drawBackValue = drawBackValue;
			this.invoiceNo = invoiceNo;
			this.invoiceDate = invoiceDate;
			this.haz = haz;
			this.unNo = unNo;
			this.newCommodity = newCommodity;
			this.invoiceAssesed = invoiceAssesed;
			this.assesmentId = assesmentId;
			this.crgInvoiceNo = crgInvoiceNo;
			this.cartingType = cartingType;
			this.isBos = isBos;
			this.zeroCartingFlag = zeroCartingFlag;
			this.zeroCartingDate = zeroCartingDate;
			this.zeroCartingApproval = zeroCartingApproval;
			this.zeroRemarks = zeroRemarks;
			this.bufferStuffing = bufferStuffing;
			this.lastStorageInvoiceDate = lastStorageInvoiceDate;
			this.lastStorageInvoiceDone = lastStorageInvoiceDone;
			this.storageInvoiceAssesed = storageInvoiceAssesed;
			this.storageAssesmentId = storageAssesmentId;
			this.scmtrCharge = scmtrCharge;
		}

		
		
		

		public String getSbNo() {
			return sbNo;
		}


		public void setSbNo(String sbNo) {
			this.sbNo = sbNo;
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

		public String getSbLineNo() {
			return sbLineNo;
		}

		public void setSbLineNo(String sbLineNo) {
			this.sbLineNo = sbLineNo;
		}

		public String getCommodity() {
			return commodity;
		}

		public void setCommodity(String commodity) {
			this.commodity = commodity;
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

		public BigDecimal getNoOfPackages() {
			return noOfPackages;
		}

		public void setNoOfPackages(BigDecimal noOfPackages) {
			this.noOfPackages = noOfPackages;
		}

		public BigDecimal getGateInPackages() {
			return gateInPackages;
		}

		public void setGateInPackages(BigDecimal gateInPackages) {
			this.gateInPackages = gateInPackages;
		}

		public BigDecimal getCartedPackages() {
			return cartedPackages;
		}

		public void setCartedPackages(BigDecimal cartedPackages) {
			this.cartedPackages = cartedPackages;
		}

		public Integer getStuffReqQty() {
			return stuffReqQty;
		}

		public void setStuffReqQty(Integer stuffReqQty) {
			this.stuffReqQty = stuffReqQty;
		}

		public BigDecimal getStuffedQty() {
			return stuffedQty;
		}

		public void setStuffedQty(BigDecimal stuffedQty) {
			this.stuffedQty = stuffedQty;
		}

		public Integer getBackToTownPack() {
			return backToTownPack;
		}

		public void setBackToTownPack(Integer backToTownPack) {
			this.backToTownPack = backToTownPack;
		}

		public BigDecimal getBttOutQty() {
			return bttOutQty;
		}

		public void setBttOutQty(BigDecimal bttOutQty) {
			this.bttOutQty = bttOutQty;
		}

		public BigDecimal getTransferPackages() {
			return transferPackages;
		}

		public void setTransferPackages(BigDecimal transferPackages) {
			this.transferPackages = transferPackages;
		}

		public BigDecimal getNilPackages() {
			return nilPackages;
		}

		public void setNilPackages(BigDecimal nilPackages) {
			this.nilPackages = nilPackages;
		}

		public String getTypeOfPackage() {
			return typeOfPackage;
		}

		public void setTypeOfPackage(String typeOfPackage) {
			this.typeOfPackage = typeOfPackage;
		}

		public BigDecimal getGrossWeight() {
			return grossWeight;
		}

		public void setGrossWeight(BigDecimal grossWeight) {
			this.grossWeight = grossWeight;
		}

		public BigDecimal getFob() {
			return fob;
		}

		public void setFob(BigDecimal fob) {
			this.fob = fob;
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

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String gethSbTransId() {
			return hSbTransId;
		}

		public void sethSbTransId(String hSbTransId) {
			this.hSbTransId = hSbTransId;
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

		public BigDecimal getDrawBackValue() {
			return drawBackValue;
		}

		public void setDrawBackValue(BigDecimal drawBackValue) {
			this.drawBackValue = drawBackValue;
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

		public String getHaz() {
			return haz;
		}

		public void setHaz(String haz) {
			this.haz = haz;
		}

		public String getUnNo() {
			return unNo;
		}

		public void setUnNo(String unNo) {
			this.unNo = unNo;
		}

		public String getNewCommodity() {
			return newCommodity;
		}

		public void setNewCommodity(String newCommodity) {
			this.newCommodity = newCommodity;
		}

		public String getInvoiceAssesed() {
			return invoiceAssesed;
		}

		public void setInvoiceAssesed(String invoiceAssesed) {
			this.invoiceAssesed = invoiceAssesed;
		}

		public String getAssesmentId() {
			return assesmentId;
		}

		public void setAssesmentId(String assesmentId) {
			this.assesmentId = assesmentId;
		}

		public String getCrgInvoiceNo() {
			return crgInvoiceNo;
		}

		public void setCrgInvoiceNo(String crgInvoiceNo) {
			this.crgInvoiceNo = crgInvoiceNo;
		}

		public String getCartingType() {
			return cartingType;
		}

		public void setCartingType(String cartingType) {
			this.cartingType = cartingType;
		}

		public String getIsBos() {
			return isBos;
		}

		public void setIsBos(String isBos) {
			this.isBos = isBos;
		}

		public String getZeroCartingFlag() {
			return zeroCartingFlag;
		}

		public void setZeroCartingFlag(String zeroCartingFlag) {
			this.zeroCartingFlag = zeroCartingFlag;
		}

		public Date getZeroCartingDate() {
			return zeroCartingDate;
		}

		public void setZeroCartingDate(Date zeroCartingDate) {
			this.zeroCartingDate = zeroCartingDate;
		}

		public String getZeroCartingApproval() {
			return zeroCartingApproval;
		}

		public void setZeroCartingApproval(String zeroCartingApproval) {
			this.zeroCartingApproval = zeroCartingApproval;
		}

		public String getZeroRemarks() {
			return zeroRemarks;
		}

		public void setZeroRemarks(String zeroRemarks) {
			this.zeroRemarks = zeroRemarks;
		}

		public String getBufferStuffing() {
			return bufferStuffing;
		}

		public void setBufferStuffing(String bufferStuffing) {
			this.bufferStuffing = bufferStuffing;
		}

		public Date getLastStorageInvoiceDate() {
			return lastStorageInvoiceDate;
		}

		public void setLastStorageInvoiceDate(Date lastStorageInvoiceDate) {
			this.lastStorageInvoiceDate = lastStorageInvoiceDate;
		}

		public String getLastStorageInvoiceDone() {
			return lastStorageInvoiceDone;
		}

		public void setLastStorageInvoiceDone(String lastStorageInvoiceDone) {
			this.lastStorageInvoiceDone = lastStorageInvoiceDone;
		}

		public String getStorageInvoiceAssesed() {
			return storageInvoiceAssesed;
		}

		public void setStorageInvoiceAssesed(String storageInvoiceAssesed) {
			this.storageInvoiceAssesed = storageInvoiceAssesed;
		}

		public String getStorageAssesmentId() {
			return storageAssesmentId;
		}

		public void setStorageAssesmentId(String storageAssesmentId) {
			this.storageAssesmentId = storageAssesmentId;
		}

		public String getScmtrCharge() {
			return scmtrCharge;
		}

		public void setScmtrCharge(String scmtrCharge) {
			this.scmtrCharge = scmtrCharge;
		}

		

		public Long getSrno() {
			return srno;
		}


		public void setSrno(Long srno) {
			this.srno = srno;
		}


		@Override
		public String toString() {
			return "ExportSbCargoEntry [srno=" + srno + ", companyId=" + companyId + ", branchId=" + branchId
					+ ", finYear=" + finYear + ", profitcentreId=" + profitcentreId + ", sbTransId=" + sbTransId
					+ ", sbLineNo=" + sbLineNo + ", sbNo=" + sbNo + ", commodity=" + commodity + ", sbDate=" + sbDate
					+ ", numberOfMarks=" + numberOfMarks + ", noOfPackages=" + noOfPackages + ", gateInPackages="
					+ gateInPackages + ", cartedPackages=" + cartedPackages + ", stuffReqQty=" + stuffReqQty
					+ ", stuffedQty=" + stuffedQty + ", backToTownPack=" + backToTownPack + ", bttOutQty=" + bttOutQty
					+ ", transferPackages=" + transferPackages + ", nilPackages=" + nilPackages + ", typeOfPackage="
					+ typeOfPackage + ", grossWeight=" + grossWeight + ", fob=" + fob + ", createdBy=" + createdBy
					+ ", createdDate=" + createdDate + ", approvedBy=" + approvedBy + ", approvedDate=" + approvedDate
					+ ", status=" + status + ", hSbTransId=" + hSbTransId + ", sbType=" + sbType + ", cargoType="
					+ cargoType + ", drawBackValue=" + drawBackValue + ", invoiceNo=" + invoiceNo + ", invoiceDate="
					+ invoiceDate + ", haz=" + haz + ", unNo=" + unNo + ", newCommodity=" + newCommodity
					+ ", invoiceAssesed=" + invoiceAssesed + ", assesmentId=" + assesmentId + ", crgInvoiceNo="
					+ crgInvoiceNo + ", cartingType=" + cartingType + ", isBos=" + isBos + ", zeroCartingFlag="
					+ zeroCartingFlag + ", zeroCartingDate=" + zeroCartingDate + ", zeroCartingApproval="
					+ zeroCartingApproval + ", zeroRemarks=" + zeroRemarks + ", bufferStuffing=" + bufferStuffing
					+ ", lastStorageInvoiceDate=" + lastStorageInvoiceDate + ", lastStorageInvoiceDone="
					+ lastStorageInvoiceDone + ", storageInvoiceAssesed=" + storageInvoiceAssesed
					+ ", storageAssesmentId=" + storageAssesmentId + ", scmtrCharge=" + scmtrCharge + "]";
		}


//		Export Main Search
		
		
		
		public ExportSbCargoEntry(String profitcentreId, String sbTransId, long srno, String sbNo,
				BigDecimal noOfPackages, BigDecimal gateInPackages, BigDecimal cartedPackages, Integer stuffReqQty,
				BigDecimal stuffedQty, BigDecimal bttOutQty, BigDecimal transferPackages, String hSbTransId,
				String sbType, Integer backToTownPack) {
			super();
			this.profitcentreId = profitcentreId;
			this.sbTransId = sbTransId;
			this.srno = srno;
			this.sbNo = sbNo;
			this.noOfPackages = noOfPackages;
			this.gateInPackages = gateInPackages;
			this.cartedPackages = cartedPackages;
			this.stuffReqQty = stuffReqQty;
			this.stuffedQty = stuffedQty;
			this.bttOutQty = bttOutQty;
			this.transferPackages = transferPackages;
			this.hSbTransId = hSbTransId;
			this.sbType = sbType;
			this.backToTownPack = backToTownPack;
			
		}


		
		
			    
}
