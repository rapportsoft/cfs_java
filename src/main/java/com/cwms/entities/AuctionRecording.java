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
@Table(name="cfauc")
@IdClass(AuctionRecordingId.class)
public class AuctionRecording {

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
	@Column(name = "Auction_No", length = 15)
	private String auctionNo;
	
	@Id
	@Column(name = "Sr_No")
	private int srNo;
	
	
	@Column(name = "Auction_Item_no", length = 3)
	private String auctionItemNo;
	
	@Column(name = "Auction_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date auctionDate;
	
	@Column(name = "Bid_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date bidDate;
	
	@Column(name = "Profitcentre_Id", length = 6)
	private String profitcentreId;
	
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
	
	@Column(name = "Shift", length = 6)
	private String shift;
	
	@Column(name = "Commodity_Description", length = 250)
	private String commodityDescription;

	@Column(name = "Actual_No_Of_Packages", precision = 8, scale = 0)
	private BigDecimal actualNoOfPackages=BigDecimal.ZERO;
	
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
	
	@Column(name = "Bidder_Name", length = 35)
	private String bidderName;
	
	@Column(name = "Bidder_Address1", length = 35)
	private String bidderAddress1;
	
	@Column(name = "Bidder_Address2", length = 35)
	private String bidderAddress2;
	
	@Column(name = "Bidder_Address3", length = 35)
	private String bidderAddress3;
	
	@Column(name = "Stc", precision = 5, scale = 2)
	private BigDecimal stc;
	
	@Column(name = "Bidder_Amount", precision = 15, scale = 2)
	private BigDecimal bidderAmount;
	
	@Column(name = "Amount_Paid", precision = 15, scale = 2)
	private BigDecimal amountPaid;
	
	@Column(name = "Confirm_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date confirmDate;
	
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

	public AuctionRecording() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public AuctionRecording(String companyId, String branchId, String bidId, String auctionNo, int srNo,
			String auctionItemNo, Date auctionDate, Date bidDate, String profitcentreId, String igmTransId,
			Date igmTransDate, String igmNo, Date igmDate, String igmLineNo, String shift, String commodityDescription,
			BigDecimal actualNoOfPackages, BigDecimal grossWt, String uom, BigDecimal duty, BigDecimal cfsDues,
			BigDecimal reservePrice, String bidderName, String bidderAddress1, String bidderAddress2,
			String bidderAddress3, BigDecimal stc, BigDecimal bidderAmount, BigDecimal amountPaid, Date confirmDate,
			String createdBy, Date createdDate, String editedBy, Date editedDate, String approvedBy, Date approvedDate,
			String status) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.bidId = bidId;
		this.auctionNo = auctionNo;
		this.srNo = srNo;
		this.auctionItemNo = auctionItemNo;
		this.auctionDate = auctionDate;
		this.bidDate = bidDate;
		this.profitcentreId = profitcentreId;
		this.igmTransId = igmTransId;
		this.igmTransDate = igmTransDate;
		this.igmNo = igmNo;
		this.igmDate = igmDate;
		this.igmLineNo = igmLineNo;
		this.shift = shift;
		this.commodityDescription = commodityDescription;
		this.actualNoOfPackages = actualNoOfPackages;
		this.grossWt = grossWt;
		this.uom = uom;
		this.duty = duty;
		this.cfsDues = cfsDues;
		this.reservePrice = reservePrice;
		this.bidderName = bidderName;
		this.bidderAddress1 = bidderAddress1;
		this.bidderAddress2 = bidderAddress2;
		this.bidderAddress3 = bidderAddress3;
		this.stc = stc;
		this.bidderAmount = bidderAmount;
		this.amountPaid = amountPaid;
		this.confirmDate = confirmDate;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.editedBy = editedBy;
		this.editedDate = editedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.status = status;
	}

	

	public BigDecimal getBidderAmount() {
		return bidderAmount;
	}


	public void setBidderAmount(BigDecimal bidderAmount) {
		this.bidderAmount = bidderAmount;
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

	public String getAuctionNo() {
		return auctionNo;
	}

	public void setAuctionNo(String auctionNo) {
		this.auctionNo = auctionNo;
	}

	public int getSrNo() {
		return srNo;
	}

	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}

	public String getAuctionItemNo() {
		return auctionItemNo;
	}

	public void setAuctionItemNo(String auctionItemNo) {
		this.auctionItemNo = auctionItemNo;
	}

	public Date getAuctionDate() {
		return auctionDate;
	}

	public void setAuctionDate(Date auctionDate) {
		this.auctionDate = auctionDate;
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

	public String getShift() {
		return shift;
	}

	public void setShift(String shift) {
		this.shift = shift;
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

	public String getBidderName() {
		return bidderName;
	}

	public void setBidderName(String bidderName) {
		this.bidderName = bidderName;
	}

	public String getBidderAddress1() {
		return bidderAddress1;
	}

	public void setBidderAddress1(String bidderAddress1) {
		this.bidderAddress1 = bidderAddress1;
	}

	public String getBidderAddress2() {
		return bidderAddress2;
	}

	public void setBidderAddress2(String bidderAddress2) {
		this.bidderAddress2 = bidderAddress2;
	}

	public String getBidderAddress3() {
		return bidderAddress3;
	}

	public void setBidderAddress3(String bidderAddress3) {
		this.bidderAddress3 = bidderAddress3;
	}

	public BigDecimal getStc() {
		return stc;
	}

	public void setStc(BigDecimal stc) {
		this.stc = stc;
	}

	public BigDecimal getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(BigDecimal amountPaid) {
		this.amountPaid = amountPaid;
	}

	public Date getConfirmDate() {
		return confirmDate;
	}

	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
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


	public AuctionRecording(String bidId, String auctionNo, int srNo, String auctionItemNo, Date auctionDate,
			Date bidDate, String profitcentreId, String igmNo, Date igmDate, String igmLineNo, String shift,
			String commodityDescription, BigDecimal actualNoOfPackages, BigDecimal grossWt, String uom, BigDecimal duty,
			BigDecimal cfsDues, BigDecimal reservePrice, String bidderName, String bidderAddress1,
			String bidderAddress2, String bidderAddress3, BigDecimal stc, BigDecimal bidderAmount,
			BigDecimal amountPaid, Date confirmDate, String createdBy, String status) {
		super();
		this.bidId = bidId;
		this.auctionNo = auctionNo;
		this.srNo = srNo;
		this.auctionItemNo = auctionItemNo;
		this.auctionDate = auctionDate;
		this.bidDate = bidDate;
		this.profitcentreId = profitcentreId;
		this.igmNo = igmNo;
		this.igmDate = igmDate;
		this.igmLineNo = igmLineNo;
		this.shift = shift;
		this.commodityDescription = commodityDescription;
		this.actualNoOfPackages = actualNoOfPackages;
		this.grossWt = grossWt;
		this.uom = uom;
		this.duty = duty;
		this.cfsDues = cfsDues;
		this.reservePrice = reservePrice;
		this.bidderName = bidderName;
		this.bidderAddress1 = bidderAddress1;
		this.bidderAddress2 = bidderAddress2;
		this.bidderAddress3 = bidderAddress3;
		this.stc = stc;
		this.bidderAmount = bidderAmount;
		this.amountPaid = amountPaid;
		this.confirmDate = confirmDate;
		this.createdBy = createdBy;
		this.status = status;
	}
	
	
	
	
	
}
