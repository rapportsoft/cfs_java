package com.cwms.entities;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "cfauccn")
@IdClass(AuctionId.class)
public class Auction {

    @Id
    @Column(name = "Company_Id", length = 6, nullable = true)
    private String companyId;

    @Id
    @Column(name = "Branch_Id", length = 6, nullable = true)
    private String branchId;

    @Id
    @Column(name = "Notice_Id", length = 10, nullable = true)
    private String noticeId;

    @Id
    @Column(name = "Container_No", length = 11, nullable = true)
    private String containerNo;

    @Id
    @Column(name = "Notice_Amnd_No", length = 3, nullable = true)
    private String noticeAmndNo;

    @Id
    @Column(name = "Final_Notice_Id", length = 10, nullable = true)
    private String finalNoticeId;

    @Column(name = "Notice_Type", length = 1, nullable = true)
    private String noticeType;

    @Column(name = "Profitcentre_Id", length = 6, nullable = true)
    private String profitcentreId;

    @Column(name = "Container_Size", length = 6, nullable = true)
    private String containerSize;

    @Column(name = "Container_Type", length = 6, nullable = true)
    private String containerType;

    @Column(name = "Gate_In_Id", length = 10, nullable = true)
    private String gateInId;

    @Column(name = "Gate_In_Date", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date gateInDate;

    @Column(name = "No_Of_Packages", precision = 8, scale = 0, nullable = true)
    private BigDecimal noOfPackages;

    @Column(name = "Gross_Wt", precision = 15, scale = 3, nullable = true)
    private BigDecimal grossWt;

    @Column(name = "Created_By", length = 10, nullable = true)
    private String createdBy;

    @Column(name = "Created_Date", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name = "Edited_By", length = 10, nullable = true)
    private String editedBy;

    @Column(name = "Edited_Date", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date editedDate;

    @Column(name = "Approved_By", length = 10, nullable = true)
    private String approvedBy;

    @Column(name = "Approved_Date", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date approvedDate;

    @Column(name = "Status", length = 1, nullable = true)
    private String status;

    @Column(name = "Container_status", length = 3)
    private String containerStatus;

    @Column(name = "File_No", length = 50)
    private String fileNo;

    @Column(name = "Lot_No", length = 50)
    private String lotNo;

    @Column(name = "HSN_No", length = 50)
    private String hsnNo;

    @Column(name = "Auction_Status", length = 1, nullable = true)
    private String auctionStatus;

    @Column(name = "File_Staus", length = 50)
    private String fileStatus;

    @Column(name = "Invoice_Assesed", length = 1, nullable = true)
    private String invoiceAssesed;

    @Column(name = "Assesment_Id", length = 20, nullable = true)
    private String assesmentId;

    @Column(name = "Invoice_No", length = 16, nullable = true)
    private String invoiceNo;

    @Column(name = "Invoice_Date", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date invoiceDate;

    @Column(name = "Credit_Type", length = 1, nullable = true)
    private String creditType;

    @Column(name = "Invoice_Category", length = 10, nullable = true)
    private String invoiceCategory;

    @Column(name = "Bill_Amt", precision = 16, scale = 3, nullable = true)
    private BigDecimal billAmt=BigDecimal.ZERO;

    @Column(name = "Invoice_Amt", precision = 16, scale = 3, nullable = true)
    private BigDecimal invoiceAmt=BigDecimal.ZERO;

    @Column(name = "Last_Assesment_Id", length = 20, nullable = true)
    private String lastAssesmentId;

    @Column(name = "Last_Assesment_Date", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastAssesmentDate;

    @Column(name = "Last_Invoice_No", length = 16, nullable = true)
    private String lastInvoiceNo;

    @Column(name = "Last_Invoice_Date", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastInvoiceDate;

    @Column(name = "Last_Invoice_Assesed", length = 1, nullable = true)
    private String lastInvoiceAssesed;

    @Column(name = "Last_Bill_Amt", precision = 16, scale = 3, nullable = true)
    private BigDecimal lastBillAmt=BigDecimal.ZERO;

    @Column(name = "Last_Invoice_Amt", precision = 16, scale = 3, nullable = true)
    private BigDecimal lastInvoiceAmt =BigDecimal.ZERO;

    
    
    
    public Auction() {
		super();
		// TODO Auto-generated constructor stub
	}





	public Auction(String companyId, String branchId, String noticeId, String containerNo, String noticeAmndNo,
			String finalNoticeId, String noticeType, String profitcentreId, String containerSize, String containerType,
			String gateInId, Date gateInDate, BigDecimal noOfPackages, BigDecimal grossWt, String createdBy,
			Date createdDate, String editedBy, Date editedDate, String approvedBy, Date approvedDate, String status,
			String containerStatus, String fileNo, String lotNo, String hsnNo, String auctionStatus, String fileStatus,
			String invoiceAssesed, String assesmentId, String invoiceNo, Date invoiceDate, String creditType,
			String invoiceCategory, BigDecimal billAmt, BigDecimal invoiceAmt, String lastAssesmentId,
			Date lastAssesmentDate, String lastInvoiceNo, Date lastInvoiceDate, String lastInvoiceAssesed,
			BigDecimal lastBillAmt, BigDecimal lastInvoiceAmt) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.noticeId = noticeId;
		this.containerNo = containerNo;
		this.noticeAmndNo = noticeAmndNo;
		this.finalNoticeId = finalNoticeId;
		this.noticeType = noticeType;
		this.profitcentreId = profitcentreId;
		this.containerSize = containerSize;
		this.containerType = containerType;
		this.gateInId = gateInId;
		this.gateInDate = gateInDate;
		this.noOfPackages = noOfPackages;
		this.grossWt = grossWt;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.editedBy = editedBy;
		this.editedDate = editedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.status = status;
		this.containerStatus = containerStatus;
		this.fileNo = fileNo;
		this.lotNo = lotNo;
		this.hsnNo = hsnNo;
		this.auctionStatus = auctionStatus;
		this.fileStatus = fileStatus;
		this.invoiceAssesed = invoiceAssesed;
		this.assesmentId = assesmentId;
		this.invoiceNo = invoiceNo;
		this.invoiceDate = invoiceDate;
		this.creditType = creditType;
		this.invoiceCategory = invoiceCategory;
		this.billAmt = billAmt;
		this.invoiceAmt = invoiceAmt;
		this.lastAssesmentId = lastAssesmentId;
		this.lastAssesmentDate = lastAssesmentDate;
		this.lastInvoiceNo = lastInvoiceNo;
		this.lastInvoiceDate = lastInvoiceDate;
		this.lastInvoiceAssesed = lastInvoiceAssesed;
		this.lastBillAmt = lastBillAmt;
		this.lastInvoiceAmt = lastInvoiceAmt;
	}





	// Getters and Setters
    // (Add getters and setters for all fields here)

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





	public String getNoticeId() {
		return noticeId;
	}





	public void setNoticeId(String noticeId) {
		this.noticeId = noticeId;
	}





	public String getContainerNo() {
		return containerNo;
	}





	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
	}





	public String getNoticeAmndNo() {
		return noticeAmndNo;
	}





	public void setNoticeAmndNo(String noticeAmndNo) {
		this.noticeAmndNo = noticeAmndNo;
	}





	public String getFinalNoticeId() {
		return finalNoticeId;
	}





	public void setFinalNoticeId(String finalNoticeId) {
		this.finalNoticeId = finalNoticeId;
	}





	public String getNoticeType() {
		return noticeType;
	}





	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
	}





	public String getProfitcentreId() {
		return profitcentreId;
	}





	public void setProfitcentreId(String profitcentreId) {
		this.profitcentreId = profitcentreId;
	}





	public String getContainerSize() {
		return containerSize;
	}





	public void setContainerSize(String containerSize) {
		this.containerSize = containerSize;
	}





	public String getContainerType() {
		return containerType;
	}





	public void setContainerType(String containerType) {
		this.containerType = containerType;
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





	public BigDecimal getNoOfPackages() {
		return noOfPackages;
	}





	public void setNoOfPackages(BigDecimal noOfPackages) {
		this.noOfPackages = noOfPackages;
	}





	public BigDecimal getGrossWt() {
		return grossWt;
	}





	public void setGrossWt(BigDecimal grossWt) {
		this.grossWt = grossWt;
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





	public String getStatus() {
		return status;
	}





	public void setStatus(String status) {
		this.status = status;
	}





	public String getContainerStatus() {
		return containerStatus;
	}





	public void setContainerStatus(String containerStatus) {
		this.containerStatus = containerStatus;
	}





	public String getFileNo() {
		return fileNo;
	}





	public void setFileNo(String fileNo) {
		this.fileNo = fileNo;
	}





	public String getLotNo() {
		return lotNo;
	}





	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
	}





	public String getHsnNo() {
		return hsnNo;
	}





	public void setHsnNo(String hsnNo) {
		this.hsnNo = hsnNo;
	}





	public String getAuctionStatus() {
		return auctionStatus;
	}





	public void setAuctionStatus(String auctionStatus) {
		this.auctionStatus = auctionStatus;
	}





	public String getFileStatus() {
		return fileStatus;
	}





	public void setFileStatus(String fileStatus) {
		this.fileStatus = fileStatus;
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





	public String getLastAssesmentId() {
		return lastAssesmentId;
	}





	public void setLastAssesmentId(String lastAssesmentId) {
		this.lastAssesmentId = lastAssesmentId;
	}





	public Date getLastAssesmentDate() {
		return lastAssesmentDate;
	}





	public void setLastAssesmentDate(Date lastAssesmentDate) {
		this.lastAssesmentDate = lastAssesmentDate;
	}





	public String getLastInvoiceNo() {
		return lastInvoiceNo;
	}





	public void setLastInvoiceNo(String lastInvoiceNo) {
		this.lastInvoiceNo = lastInvoiceNo;
	}





	public Date getLastInvoiceDate() {
		return lastInvoiceDate;
	}





	public void setLastInvoiceDate(Date lastInvoiceDate) {
		this.lastInvoiceDate = lastInvoiceDate;
	}





	public String getLastInvoiceAssesed() {
		return lastInvoiceAssesed;
	}





	public void setLastInvoiceAssesed(String lastInvoiceAssesed) {
		this.lastInvoiceAssesed = lastInvoiceAssesed;
	}





	public BigDecimal getLastBillAmt() {
		return lastBillAmt;
	}





	public void setLastBillAmt(BigDecimal lastBillAmt) {
		this.lastBillAmt = lastBillAmt;
	}





	public BigDecimal getLastInvoiceAmt() {
		return lastInvoiceAmt;
	}





	public void setLastInvoiceAmt(BigDecimal lastInvoiceAmt) {
		this.lastInvoiceAmt = lastInvoiceAmt;
	}





	// toString() Method
    @Override
    public String toString() {
        return "Cfauccn{" +
                "companyId='" + companyId + '\'' +
                ", branchId='" + branchId + '\'' +
                ", noticeId='" + noticeId + '\'' +
                ", containerNo='" + containerNo + '\'' +
                ", noticeAmndNo='" + noticeAmndNo + '\'' +
                ", finalNoticeId='" + finalNoticeId + '\'' +
                ", noticeType='" + noticeType + '\'' +
                ", profitcentreId='" + profitcentreId + '\'' +
                ", containerSize='" + containerSize + '\'' +
                ", containerType='" + containerType + '\'' +
                ", gateInId='" + gateInId + '\'' +
                ", gateInDate=" + gateInDate +
                ", noOfPackages=" + noOfPackages +
                ", grossWt=" + grossWt +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                ", editedBy='" + editedBy + '\'' +
                ", editedDate=" + editedDate +
                ", approvedBy='" + approvedBy + '\'' +
                ", approvedDate=" + approvedDate +
                ", status='" + status + '\'' +
                ", containerStatus='" + containerStatus + '\'' +
                ", fileNo='" + fileNo + '\'' +
                ", lotNo='" + lotNo + '\'' +
                ", hsnNo='" + hsnNo + '\'' +
                ", auctionStatus='" + auctionStatus + '\'' +
                ", fileStatus='" + fileStatus + '\'' +
                ", invoiceAssesed='" + invoiceAssesed + '\'' +
                ", assesmentId='" + assesmentId + '\'' +
                ", invoiceNo='" + invoiceNo + '\'' +
                ", invoiceDate=" + invoiceDate +
                ", creditType='" + creditType + '\'' +
                ", invoiceCategory='" + invoiceCategory + '\'' +
                ", billAmt=" + billAmt +
                ", invoiceAmt=" + invoiceAmt +
                ", lastAssesmentId='" + lastAssesmentId + '\'' +
                ", lastAssesmentDate=" + lastAssesmentDate +
                ", lastInvoiceNo='" + lastInvoiceNo + '\'' +
                ", lastInvoiceDate=" + lastInvoiceDate +
                ", lastInvoiceAssesed='" + lastInvoiceAssesed + '\'' +
                ", lastBillAmt=" + lastBillAmt +
                ", lastInvoiceAmt=" + lastInvoiceAmt +
                '}';
    }





    // to get saved container detailed record after save please check 
	public Auction(String containerNo, String containerSize, String containerType,  Date gateInDate,
			BigDecimal noOfPackages, BigDecimal grossWt,String gateInId) {
		super();
		this.containerNo = containerNo;
		this.containerSize = containerSize;
		this.containerType = containerType;
		this.gateInDate = gateInDate;
		this.noOfPackages = noOfPackages;
		this.grossWt = grossWt;
		this.gateInId = gateInId;
	}
    
    
}