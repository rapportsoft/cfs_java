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
@Table(name="cfaucbid")
@IdClass(AuctionBidId.class)
public class AuctionBid {

	@Id
	@Column(name = "Company_Id", length = 6)
	private String companyId;

	@Id
	@Column(name = "Branch_Id", length = 6)
	private String branchId;
	
	@Id
	@Column(name = "Bid_Id", length = 10)
	private String bidId;
	
	@Id
	@Column(name = "Auction_Item_no", length = 3)
	private String auctionItemNo;
	
	@Column(name = "Bid_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date bidDate;
	
	@Column(name = "Profitcentre_Id", length = 6)
	private String profitcentreId;
	
	@Column(name = "Notice_Id", length = 10)
	private String noticeId;
	
	@Column(name = "Notice_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date noticeDate;
	
	@Column(name = "Shift", length = 6)
	private String shift;
	
	@Column(name = "IGM_Trans_Id", length = 10)
	private String igmTransId;

	@Column(name = "IGM_Trans_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date igmTransDate;

	@Column(name = "IGM_No", length = 10)
	private String igmNo;

	@Column(name = "IGM_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date igmDate;

	@Column(name = "IGM_Line_No", length = 7)
	private String igmLineNo;
	
	@Column(name = "Commodity_Description", length = 250)
	private String commodityDescription;

	@Column(name = "Actual_No_Of_Packages", precision = 8, scale = 0)
	private BigDecimal actualNoOfPackages=BigDecimal.ZERO;
	
	@Column(name = "Package_Type", length = 6)
	private String packageType;

	@Column(name = "Gross_Wt", precision = 15, scale = 3)
	private BigDecimal grossWt;

	@Column(name = "UOM", length = 6)
	private String uom;
	
	@Column(name = "Duty", precision = 15, scale = 2)
	private BigDecimal duty;
	
	@Column(name = "CFS_Dues", precision = 15, scale = 2)
	private BigDecimal cfsDues;
	
	@Column(name = "Reserve_Price", precision = 15, scale = 2)
	private BigDecimal reservePrice;
	
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
	
	@Column(name = "Status", length = 1)
	private String status;
	
	

	public AuctionBid() {
		super();
		// TODO Auto-generated constructor stub
	}



	public AuctionBid(String companyId, String branchId, String bidId, String auctionItemNo, Date bidDate,
			String profitcentreId, String noticeId, Date noticeDate, String shift, String igmTransId, Date igmTransDate,
			String igmNo, Date igmDate, String igmLineNo, String commodityDescription, BigDecimal actualNoOfPackages,
			String packageType, BigDecimal grossWt, String uom, BigDecimal duty, BigDecimal cfsDues,
			BigDecimal reservePrice, String createdBy, Date createdDate, String editedBy, Date editedDate,
			String approvedBy, Date approvedDate, String status) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.bidId = bidId;
		this.auctionItemNo = auctionItemNo;
		this.bidDate = bidDate;
		this.profitcentreId = profitcentreId;
		this.noticeId = noticeId;
		this.noticeDate = noticeDate;
		this.shift = shift;
		this.igmTransId = igmTransId;
		this.igmTransDate = igmTransDate;
		this.igmNo = igmNo;
		this.igmDate = igmDate;
		this.igmLineNo = igmLineNo;
		this.commodityDescription = commodityDescription;
		this.actualNoOfPackages = actualNoOfPackages;
		this.packageType = packageType;
		this.grossWt = grossWt;
		this.uom = uom;
		this.duty = duty;
		this.cfsDues = cfsDues;
		this.reservePrice = reservePrice;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.editedBy = editedBy;
		this.editedDate = editedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.status = status;
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



	public String getBidId() {
		return bidId;
	}



	public void setBidId(String bidId) {
		this.bidId = bidId;
	}



	public String getAuctionItemNo() {
		return auctionItemNo;
	}



	public void setAuctionItemNo(String auctionItemNo) {
		this.auctionItemNo = auctionItemNo;
	}



	public Date getBidDate() {
		return bidDate;
	}



	public void setBidDate(Date bidDate) {
		this.bidDate = bidDate;
	}



	public String getProfitcentreId() {
		return profitcentreId;
	}



	public void setProfitcentreId(String profitcentreId) {
		this.profitcentreId = profitcentreId;
	}



	public String getNoticeId() {
		return noticeId;
	}



	public void setNoticeId(String noticeId) {
		this.noticeId = noticeId;
	}



	public Date getNoticeDate() {
		return noticeDate;
	}



	public void setNoticeDate(Date noticeDate) {
		this.noticeDate = noticeDate;
	}



	public String getShift() {
		return shift;
	}



	public void setShift(String shift) {
		this.shift = shift;
	}



	public String getIgmTransId() {
		return igmTransId;
	}



	public void setIgmTransId(String igmTransId) {
		this.igmTransId = igmTransId;
	}



	public Date getIgmTransDate() {
		return igmTransDate;
	}



	public void setIgmTransDate(Date igmTransDate) {
		this.igmTransDate = igmTransDate;
	}



	public String getIgmNo() {
		return igmNo;
	}



	public void setIgmNo(String igmNo) {
		this.igmNo = igmNo;
	}



	public Date getIgmDate() {
		return igmDate;
	}



	public void setIgmDate(Date igmDate) {
		this.igmDate = igmDate;
	}



	public String getIgmLineNo() {
		return igmLineNo;
	}



	public void setIgmLineNo(String igmLineNo) {
		this.igmLineNo = igmLineNo;
	}



	public String getCommodityDescription() {
		return commodityDescription;
	}



	public void setCommodityDescription(String commodityDescription) {
		this.commodityDescription = commodityDescription;
	}



	public BigDecimal getActualNoOfPackages() {
		return actualNoOfPackages;
	}



	public void setActualNoOfPackages(BigDecimal actualNoOfPackages) {
		this.actualNoOfPackages = actualNoOfPackages;
	}



	public String getPackageType() {
		return packageType;
	}



	public void setPackageType(String packageType) {
		this.packageType = packageType;
	}



	public BigDecimal getGrossWt() {
		return grossWt;
	}



	public void setGrossWt(BigDecimal grossWt) {
		this.grossWt = grossWt;
	}



	public String getUom() {
		return uom;
	}



	public void setUom(String uom) {
		this.uom = uom;
	}



	public BigDecimal getDuty() {
		return duty;
	}



	public void setDuty(BigDecimal duty) {
		this.duty = duty;
	}



	public BigDecimal getCfsDues() {
		return cfsDues;
	}



	public void setCfsDues(BigDecimal cfsDues) {
		this.cfsDues = cfsDues;
	}



	public BigDecimal getReservePrice() {
		return reservePrice;
	}



	public void setReservePrice(BigDecimal reservePrice) {
		this.reservePrice = reservePrice;
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



	public AuctionBid(String bidId, Date bidDate, String profitcentreId, String noticeId, Date noticeDate, String shift,
			String igmNo, Date igmDate, String igmLineNo, String commodityDescription, BigDecimal actualNoOfPackages,
			String packageType, BigDecimal grossWt, String uom, BigDecimal duty, BigDecimal cfsDues,
			BigDecimal reservePrice, String createdBy, String status, String auctionItemNo) {
		super();
		this.bidId = bidId;
		this.bidDate = bidDate;
		this.profitcentreId = profitcentreId;
		this.noticeId = noticeId;
		this.noticeDate = noticeDate;
		this.shift = shift;
		this.igmNo = igmNo;
		this.igmDate = igmDate;
		this.igmLineNo = igmLineNo;
		this.commodityDescription = commodityDescription;
		this.actualNoOfPackages = actualNoOfPackages;
		this.packageType = packageType;
		this.grossWt = grossWt;
		this.uom = uom;
		this.duty = duty;
		this.cfsDues = cfsDues;
		this.reservePrice = reservePrice;
		this.createdBy = createdBy;
		this.status = status;
		this.auctionItemNo = auctionItemNo;
	}
	
	
	
	
	
}
