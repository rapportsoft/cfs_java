package com.cwms.entities;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.*;

@Entity
@Table(name = "cfauccrg")
@IdClass(AuctionDetailId.class)
public class AuctionDetail {

	@Id
	@Column(name = "Company_Id", length = 6, nullable = false)
	private String companyId;

	@Id
	@Column(name = "Branch_Id", length = 6, nullable = false)
	private String branchId;

	@Id
	@Column(name = "Profitcentre_Id", length = 6, nullable = false)
	private String profitcentreId;

	@Id
	@Column(name = "Notice_Id", length = 10, nullable = false)
	private String noticeId;

	@Id
	@Column(name = "Notice_Amnd_No", length = 3, nullable = false)
	private String noticeAmndNo;

	@Id
	@Column(name = "Final_Notice_Id", length = 10, nullable = false)
	private String finalNoticeId;

	@Column(name = "Notice_Type", length = 1, nullable = false)
	private String noticeType;

	@Column(name = "Trans_type", length = 3, nullable = true)
	private String transType;

	@Column(name = "Notice_date", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date noticeDate;

	@Column(name = "IGM_Trans_Id", length = 10, nullable = false)
	private String igmTransId;

	@Column(name = "IGM_Trans_Date", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date igmTransDate;

	@Column(name = "IGM_No", length = 10)
	private String igmNo;

	@Column(name = "IGM_Date", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date igmDate;

	@Column(name = "IGM_Line_No", length = 7, nullable = false)
	private String igmLineNo;

	@Column(name = "VIA_No", length = 15, nullable = true)
	private String viaNo;

	@Column(name = "Shift", length = 6, nullable = true)
	private String shift;

	@Column(name = "Source", length = 3, nullable = true)
	private String source;

	@Column(name = "BOE_No", length = 15, nullable = true)
	private String boeNo;

	@Column(name = "BOE_Date", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date boeDate;

	@Column(name = "Vessel", length = 27, nullable = true)
	private String vessel;

	@Column(name = "SA", length = 6, nullable = true)
	private String sa;

	@Column(name = "Importer_Name", length = 60, nullable = true)
	private String importerName;

	@Column(name = "importer_address1", length = 250)
	private String importerAddress1;

	@Column(name = "importer_address2", length = 60)
	private String importerAddress2;

	@Column(name = "importer_address3", length = 60)
	private String importerAddress3;

	@Column(name = "Notify_Party", length = 60, nullable = true)
	private String notifyParty;

	@Column(name = "Notify_Party_Address1", length = 250)
	private String notifyPartyAddress1;

	@Column(name = "Notify_Party_Address2", length = 35, nullable = true)
	private String notifyPartyAddress2;

	@Column(name = "Notify_Party_Address3", length = 35, nullable = true)
	private String notifyPartyAddress3;

	@Column(name = "Commodity_Description", length = 250, nullable = true)
	private String commodityDescription;

	@Column(name = "No_of_Packages", precision = 8, scale = 0, nullable = true)
	private BigDecimal noOfPackages;

	@Column(name = "Actual_No_Of_Packages", precision = 8, scale = 0, nullable = true)
	private BigDecimal actualNoOfPackages=BigDecimal.ZERO;

	@Column(name = "Type_Of_Package", length = 6, nullable = true)
	private String typeOfPackage;

	@Column(name = "Gross_Wt", precision = 15, scale = 3, nullable = true)
	private BigDecimal grossWt;

	@Column(name = "UOM", length = 6, nullable = true)
	private String uom;

	@Column(name = "BL_No", length = 20, nullable = true)
	private String blNo;

	@Column(name = "BL_Date", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date blDate;

	@Column(name = "Assessiable_Available", length = 1, nullable = true)
	private String assessiableAvailable;

	@Column(name = "Accessable_Value_as_Valuation", precision = 15, scale = 2, nullable = true)
	private BigDecimal accessableValueAsValuation;

	@Column(name = "Rate_of_Duty", precision = 15, scale = 2, nullable = true)
	private BigDecimal rateOfDuty;

	@Column(name = "Amt_of_Duty", precision = 15, scale = 2, nullable = true)
	private BigDecimal amtOfDuty;

	@Column(name = "Duty", precision = 15, scale = 2, nullable = true)
	private BigDecimal duty;

	@Column(name = "MOP", precision = 15, scale = 2, nullable = true)
	private BigDecimal mop;

	@Column(name = "PMV", precision = 15, scale = 2, nullable = true)
	private BigDecimal pmv;

	@Column(name = "Fair_Value_of_goods", precision = 15, scale = 2, nullable = true)
	private BigDecimal fairValueOfGoods;

	@Column(name = "BID_Id", length = 10, nullable = true)
	private String bidId;

	@Column(name = "BID_Date", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date bidDate;

	@Column(name = "comments", length = 150)
	private String comments;

	@Column(name = "CV_Status", length = 1, nullable = true)
	private String cvStatus;

	@Column(name = "CV_Created_By", length = 10, nullable = true)
	private String cvCreatedBy;

	@Column(name = "CV_Created_Date", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date cvCreatedDate;

	@Column(name = "CV_Approved_By", length = 10, nullable = true)
	private String cvApprovedBy;

	@Column(name = "CV_Approved_Date", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date cvApprovedDate;

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

	@Column(name = "POL", length = 50)
	private String pol;

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

	@Column(name = "TCS", precision = 12, scale = 2, nullable = true)
	private BigDecimal tcs;

	@Column(name = "IGST", precision = 12, scale = 2, nullable = true)
	private BigDecimal igst;

	@Column(name = "SGST", precision = 12, scale = 2, nullable = true)
	private BigDecimal sgst;

	@Column(name = "CGST", precision = 12, scale = 2, nullable = true)
	private BigDecimal cgst;

	@Column(name = "Auction_Type", length = 50)
	private String auctionType;

	@Column(name = "BID_Amt", precision = 15, scale = 2, nullable = true)
	private BigDecimal bidAmt;

	@Column(name = "STC_Status", length = 1, nullable = true)
	private String stcStatus;

	@Column(name = "Accsept_Reject_Status", length = 1, nullable = true)
	private String acceptRejectStatus;

	@Column(name = "GST_Approved_Date", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date gstApprovedDate;

	@Column(name = "CMD_Approved_Date", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date cmdApprovedDate;

	@Column(name = "Bidamt_Approved_Date", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date bidamtApprovedDate;

	@Column(name = "STC_Approved_Date", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date stcApprovedDate;

	@Column(name = "Custome_Accept_Reject_Date", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date customeAcceptRejectDate;

	@Column(name = "Custome_OutOf_charge_Date", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date customeOutOfChargeDate;

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

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public Date getNoticeDate() {
		return noticeDate;
	}

	public void setNoticeDate(Date noticeDate) {
		this.noticeDate = noticeDate;
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

	public String getViaNo() {
		return viaNo;
	}

	public void setViaNo(String viaNo) {
		this.viaNo = viaNo;
	}

	public String getShift() {
		return shift;
	}

	public void setShift(String shift) {
		this.shift = shift;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getBoeNo() {
		return boeNo;
	}

	public void setBoeNo(String boeNo) {
		this.boeNo = boeNo;
	}

	public Date getBoeDate() {
		return boeDate;
	}

	public void setBoeDate(Date boeDate) {
		this.boeDate = boeDate;
	}

	public String getVessel() {
		return vessel;
	}

	public void setVessel(String vessel) {
		this.vessel = vessel;
	}

	public String getSa() {
		return sa;
	}

	public void setSa(String sa) {
		this.sa = sa;
	}

	public String getImporterName() {
		return importerName;
	}

	public void setImporterName(String importerName) {
		this.importerName = importerName;
	}

	public String getImporterAddress1() {
		return importerAddress1;
	}

	public void setImporterAddress1(String importerAddress1) {
		this.importerAddress1 = importerAddress1;
	}

	public String getImporterAddress2() {
		return importerAddress2;
	}

	public void setImporterAddress2(String importerAddress2) {
		this.importerAddress2 = importerAddress2;
	}

	public String getImporterAddress3() {
		return importerAddress3;
	}

	public void setImporterAddress3(String importerAddress3) {
		this.importerAddress3 = importerAddress3;
	}

	public String getNotifyParty() {
		return notifyParty;
	}

	public void setNotifyParty(String notifyParty) {
		this.notifyParty = notifyParty;
	}

	public String getNotifyPartyAddress1() {
		return notifyPartyAddress1;
	}

	public void setNotifyPartyAddress1(String notifyPartyAddress1) {
		this.notifyPartyAddress1 = notifyPartyAddress1;
	}

	public String getNotifyPartyAddress2() {
		return notifyPartyAddress2;
	}

	public void setNotifyPartyAddress2(String notifyPartyAddress2) {
		this.notifyPartyAddress2 = notifyPartyAddress2;
	}

	public String getNotifyPartyAddress3() {
		return notifyPartyAddress3;
	}

	public void setNotifyPartyAddress3(String notifyPartyAddress3) {
		this.notifyPartyAddress3 = notifyPartyAddress3;
	}

	public String getCommodityDescription() {
		return commodityDescription;
	}

	public void setCommodityDescription(String commodityDescription) {
		this.commodityDescription = commodityDescription;
	}

	public BigDecimal getNoOfPackages() {
		return noOfPackages;
	}

	public void setNoOfPackages(BigDecimal noOfPackages) {
		this.noOfPackages = noOfPackages;
	}

	public BigDecimal getActualNoOfPackages() {
		return actualNoOfPackages;
	}

	public void setActualNoOfPackages(BigDecimal actualNoOfPackages) {
		this.actualNoOfPackages = actualNoOfPackages;
	}

	public String getTypeOfPackage() {
		return typeOfPackage;
	}

	public void setTypeOfPackage(String typeOfPackage) {
		this.typeOfPackage = typeOfPackage;
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

	public String getBlNo() {
		return blNo;
	}

	public void setBlNo(String blNo) {
		this.blNo = blNo;
	}

	public Date getBlDate() {
		return blDate;
	}

	public void setBlDate(Date blDate) {
		this.blDate = blDate;
	}

	public String getAssessiableAvailable() {
		return assessiableAvailable;
	}

	public void setAssessiableAvailable(String assessiableAvailable) {
		this.assessiableAvailable = assessiableAvailable;
	}

	public BigDecimal getAccessableValueAsValuation() {
		return accessableValueAsValuation;
	}

	public void setAccessableValueAsValuation(BigDecimal accessableValueAsValuation) {
		this.accessableValueAsValuation = accessableValueAsValuation;
	}

	public BigDecimal getRateOfDuty() {
		return rateOfDuty;
	}

	public void setRateOfDuty(BigDecimal rateOfDuty) {
		this.rateOfDuty = rateOfDuty;
	}

	public BigDecimal getAmtOfDuty() {
		return amtOfDuty;
	}

	public void setAmtOfDuty(BigDecimal amtOfDuty) {
		this.amtOfDuty = amtOfDuty;
	}

	public BigDecimal getDuty() {
		return duty;
	}

	public void setDuty(BigDecimal duty) {
		this.duty = duty;
	}

	public BigDecimal getMop() {
		return mop;
	}

	public void setMop(BigDecimal mop) {
		this.mop = mop;
	}

	public BigDecimal getPmv() {
		return pmv;
	}

	public void setPmv(BigDecimal pmv) {
		this.pmv = pmv;
	}

	public BigDecimal getFairValueOfGoods() {
		return fairValueOfGoods;
	}

	public void setFairValueOfGoods(BigDecimal fairValueOfGoods) {
		this.fairValueOfGoods = fairValueOfGoods;
	}

	public String getBidId() {
		return bidId;
	}

	public void setBidId(String bidId) {
		this.bidId = bidId;
	}

	public Date getBidDate() {
		return bidDate;
	}

	public void setBidDate(Date bidDate) {
		this.bidDate = bidDate;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getCvStatus() {
		return cvStatus;
	}

	public void setCvStatus(String cvStatus) {
		this.cvStatus = cvStatus;
	}

	public String getCvCreatedBy() {
		return cvCreatedBy;
	}

	public void setCvCreatedBy(String cvCreatedBy) {
		this.cvCreatedBy = cvCreatedBy;
	}

	public Date getCvCreatedDate() {
		return cvCreatedDate;
	}

	public void setCvCreatedDate(Date cvCreatedDate) {
		this.cvCreatedDate = cvCreatedDate;
	}

	public String getCvApprovedBy() {
		return cvApprovedBy;
	}

	public void setCvApprovedBy(String cvApprovedBy) {
		this.cvApprovedBy = cvApprovedBy;
	}

	public Date getCvApprovedDate() {
		return cvApprovedDate;
	}

	public void setCvApprovedDate(Date cvApprovedDate) {
		this.cvApprovedDate = cvApprovedDate;
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

	public String getPol() {
		return pol;
	}

	public void setPol(String pol) {
		this.pol = pol;
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

	public BigDecimal getTcs() {
		return tcs;
	}

	public void setTcs(BigDecimal tcs) {
		this.tcs = tcs;
	}

	public BigDecimal getIgst() {
		return igst;
	}

	public void setIgst(BigDecimal igst) {
		this.igst = igst;
	}

	public BigDecimal getSgst() {
		return sgst;
	}

	public void setSgst(BigDecimal sgst) {
		this.sgst = sgst;
	}

	public BigDecimal getCgst() {
		return cgst;
	}

	public void setCgst(BigDecimal cgst) {
		this.cgst = cgst;
	}

	public String getAuctionType() {
		return auctionType;
	}

	public void setAuctionType(String auctionType) {
		this.auctionType = auctionType;
	}

	public BigDecimal getBidAmt() {
		return bidAmt;
	}

	public void setBidAmt(BigDecimal bidAmt) {
		this.bidAmt = bidAmt;
	}

	public String getStcStatus() {
		return stcStatus;
	}

	public void setStcStatus(String stcStatus) {
		this.stcStatus = stcStatus;
	}

	public String getAcceptRejectStatus() {
		return acceptRejectStatus;
	}

	public void setAcceptRejectStatus(String acceptRejectStatus) {
		this.acceptRejectStatus = acceptRejectStatus;
	}

	public Date getGstApprovedDate() {
		return gstApprovedDate;
	}

	public void setGstApprovedDate(Date gstApprovedDate) {
		this.gstApprovedDate = gstApprovedDate;
	}

	public Date getCmdApprovedDate() {
		return cmdApprovedDate;
	}

	public void setCmdApprovedDate(Date cmdApprovedDate) {
		this.cmdApprovedDate = cmdApprovedDate;
	}

	public Date getBidamtApprovedDate() {
		return bidamtApprovedDate;
	}

	public void setBidamtApprovedDate(Date bidamtApprovedDate) {
		this.bidamtApprovedDate = bidamtApprovedDate;
	}

	public Date getStcApprovedDate() {
		return stcApprovedDate;
	}

	public void setStcApprovedDate(Date stcApprovedDate) {
		this.stcApprovedDate = stcApprovedDate;
	}

	public Date getCustomeAcceptRejectDate() {
		return customeAcceptRejectDate;
	}

	public void setCustomeAcceptRejectDate(Date customeAcceptRejectDate) {
		this.customeAcceptRejectDate = customeAcceptRejectDate;
	}

	public Date getCustomeOutOfChargeDate() {
		return customeOutOfChargeDate;
	}

	public void setCustomeOutOfChargeDate(Date customeOutOfChargeDate) {
		this.customeOutOfChargeDate = customeOutOfChargeDate;
	}

	public AuctionDetail(String companyId, String branchId, String profitcentreId, String noticeId, String noticeAmndNo,
			String finalNoticeId, String noticeType, String transType, Date noticeDate, String igmTransId,
			Date igmTransDate, String igmNo, Date igmDate, String igmLineNo, String viaNo, String shift, String source,
			String boeNo, Date boeDate, String vessel, String sa, String importerName, String importerAddress1,
			String importerAddress2, String importerAddress3, String notifyParty, String notifyPartyAddress1,
			String notifyPartyAddress2, String notifyPartyAddress3, String commodityDescription,
			BigDecimal noOfPackages, BigDecimal actualNoOfPackages, String typeOfPackage, BigDecimal grossWt,
			String uom, String blNo, Date blDate, String assessiableAvailable, BigDecimal accessableValueAsValuation,
			BigDecimal rateOfDuty, BigDecimal amtOfDuty, BigDecimal duty, BigDecimal mop, BigDecimal pmv,
			BigDecimal fairValueOfGoods, String bidId, Date bidDate, String comments, String cvStatus,
			String cvCreatedBy, Date cvCreatedDate, String cvApprovedBy, Date cvApprovedDate, String createdBy,
			Date createdDate, String editedBy, Date editedDate, String approvedBy, Date approvedDate, String status,
			String pol, String fileNo, String lotNo, String hsnNo, String auctionStatus, String fileStatus,
			BigDecimal tcs, BigDecimal igst, BigDecimal sgst, BigDecimal cgst, String auctionType, BigDecimal bidAmt,
			String stcStatus, String acceptRejectStatus, Date gstApprovedDate, Date cmdApprovedDate,
			Date bidamtApprovedDate, Date stcApprovedDate, Date customeAcceptRejectDate, Date customeOutOfChargeDate) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.profitcentreId = profitcentreId;
		this.noticeId = noticeId;
		this.noticeAmndNo = noticeAmndNo;
		this.finalNoticeId = finalNoticeId;
		this.noticeType = noticeType;
		this.transType = transType;
		this.noticeDate = noticeDate;
		this.igmTransId = igmTransId;
		this.igmTransDate = igmTransDate;
		this.igmNo = igmNo;
		this.igmDate = igmDate;
		this.igmLineNo = igmLineNo;
		this.viaNo = viaNo;
		this.shift = shift;
		this.source = source;
		this.boeNo = boeNo;
		this.boeDate = boeDate;
		this.vessel = vessel;
		this.sa = sa;
		this.importerName = importerName;
		this.importerAddress1 = importerAddress1;
		this.importerAddress2 = importerAddress2;
		this.importerAddress3 = importerAddress3;
		this.notifyParty = notifyParty;
		this.notifyPartyAddress1 = notifyPartyAddress1;
		this.notifyPartyAddress2 = notifyPartyAddress2;
		this.notifyPartyAddress3 = notifyPartyAddress3;
		this.commodityDescription = commodityDescription;
		this.noOfPackages = noOfPackages;
		this.actualNoOfPackages = actualNoOfPackages;
		this.typeOfPackage = typeOfPackage;
		this.grossWt = grossWt;
		this.uom = uom;
		this.blNo = blNo;
		this.blDate = blDate;
		this.assessiableAvailable = assessiableAvailable;
		this.accessableValueAsValuation = accessableValueAsValuation;
		this.rateOfDuty = rateOfDuty;
		this.amtOfDuty = amtOfDuty;
		this.duty = duty;
		this.mop = mop;
		this.pmv = pmv;
		this.fairValueOfGoods = fairValueOfGoods;
		this.bidId = bidId;
		this.bidDate = bidDate;
		this.comments = comments;
		this.cvStatus = cvStatus;
		this.cvCreatedBy = cvCreatedBy;
		this.cvCreatedDate = cvCreatedDate;
		this.cvApprovedBy = cvApprovedBy;
		this.cvApprovedDate = cvApprovedDate;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.editedBy = editedBy;
		this.editedDate = editedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.status = status;
		this.pol = pol;
		this.fileNo = fileNo;
		this.lotNo = lotNo;
		this.hsnNo = hsnNo;
		this.auctionStatus = auctionStatus;
		this.fileStatus = fileStatus;
		this.tcs = tcs;
		this.igst = igst;
		this.sgst = sgst;
		this.cgst = cgst;
		this.auctionType = auctionType;
		this.bidAmt = bidAmt;
		this.stcStatus = stcStatus;
		this.acceptRejectStatus = acceptRejectStatus;
		this.gstApprovedDate = gstApprovedDate;
		this.cmdApprovedDate = cmdApprovedDate;
		this.bidamtApprovedDate = bidamtApprovedDate;
		this.stcApprovedDate = stcApprovedDate;
		this.customeAcceptRejectDate = customeAcceptRejectDate;
		this.customeOutOfChargeDate = customeOutOfChargeDate;
	}

	public AuctionDetail() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "AuctionDetail [companyId=" + companyId + ", branchId=" + branchId + ", profitcentreId=" + profitcentreId
				+ ", noticeId=" + noticeId + ", noticeAmndNo=" + noticeAmndNo + ", finalNoticeId=" + finalNoticeId
				+ ", noticeType=" + noticeType + ", transType=" + transType + ", noticeDate=" + noticeDate
				+ ", igmTransId=" + igmTransId + ", igmTransDate=" + igmTransDate + ", igmNo=" + igmNo + ", igmDate="
				+ igmDate + ", igmLineNo=" + igmLineNo + ", viaNo=" + viaNo + ", shift=" + shift + ", source=" + source
				+ ", boeNo=" + boeNo + ", boeDate=" + boeDate + ", vessel=" + vessel + ", sa=" + sa + ", importerName="
				+ importerName + ", importerAddress1=" + importerAddress1 + ", importerAddress2=" + importerAddress2
				+ ", importerAddress3=" + importerAddress3 + ", notifyParty=" + notifyParty + ", notifyPartyAddress1="
				+ notifyPartyAddress1 + ", notifyPartyAddress2=" + notifyPartyAddress2 + ", notifyPartyAddress3="
				+ notifyPartyAddress3 + ", commodityDescription=" + commodityDescription + ", noOfPackages="
				+ noOfPackages + ", actualNoOfPackages=" + actualNoOfPackages + ", typeOfPackage=" + typeOfPackage
				+ ", grossWt=" + grossWt + ", uom=" + uom + ", blNo=" + blNo + ", blDate=" + blDate
				+ ", assessiableAvailable=" + assessiableAvailable + ", accessableValueAsValuation="
				+ accessableValueAsValuation + ", rateOfDuty=" + rateOfDuty + ", amtOfDuty=" + amtOfDuty + ", duty="
				+ duty + ", mop=" + mop + ", pmv=" + pmv + ", fairValueOfGoods=" + fairValueOfGoods + ", bidId=" + bidId
				+ ", bidDate=" + bidDate + ", comments=" + comments + ", cvStatus=" + cvStatus + ", cvCreatedBy="
				+ cvCreatedBy + ", cvCreatedDate=" + cvCreatedDate + ", cvApprovedBy=" + cvApprovedBy
				+ ", cvApprovedDate=" + cvApprovedDate + ", createdBy=" + createdBy + ", createdDate=" + createdDate
				+ ", editedBy=" + editedBy + ", editedDate=" + editedDate + ", approvedBy=" + approvedBy
				+ ", approvedDate=" + approvedDate + ", status=" + status + ", pol=" + pol + ", fileNo=" + fileNo
				+ ", lotNo=" + lotNo + ", hsnNo=" + hsnNo + ", auctionStatus=" + auctionStatus + ", fileStatus="
				+ fileStatus + ", tcs=" + tcs + ", igst=" + igst + ", sgst=" + sgst + ", cgst=" + cgst
				+ ", auctionType=" + auctionType + ", bidAmt=" + bidAmt + ", stcStatus=" + stcStatus
				+ ", acceptRejectStatus=" + acceptRejectStatus + ", gstApprovedDate=" + gstApprovedDate
				+ ", cmdApprovedDate=" + cmdApprovedDate + ", bidamtApprovedDate=" + bidamtApprovedDate
				+ ", stcApprovedDate=" + stcApprovedDate + ", customeAcceptRejectDate=" + customeAcceptRejectDate
				+ ", customeOutOfChargeDate=" + customeOutOfChargeDate + "]";
	}

	
	
	// to get data for auction notice screen search
	public AuctionDetail(String companyId, String branchId, String profitcentreId, String noticeId, String noticeAmndNo,
			String noticeType, String transType, Date noticeDate, String igmTransId, Date igmTransDate, String igmNo,
			Date igmDate, String igmLineNo, BigDecimal noOfPackages, BigDecimal grossWt, String status,String blNo) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.profitcentreId = profitcentreId;
		this.noticeId = noticeId;
		this.noticeAmndNo = noticeAmndNo;
		this.noticeType = noticeType;
		this.transType = transType;
		this.noticeDate = noticeDate;
		this.igmTransId = igmTransId;
		this.igmTransDate = igmTransDate;
		this.igmNo = igmNo;
		this.igmDate = igmDate;
		this.igmLineNo = igmLineNo;
		this.noOfPackages = noOfPackages;
		this.grossWt = grossWt;
		this.status = status;
		this.blNo = blNo;
	}

	
	
	
	
	// to get on screen data using radio button please check for any query 
	public AuctionDetail(String companyId, String branchId, String profitcentreId, String noticeId, String noticeAmndNo,
			String finalNoticeId, String noticeType, String transType, Date noticeDate, String igmTransId,
			Date igmTransDate, String igmNo, Date igmDate, String igmLineNo, String viaNo, String shift, String source,
			String boeNo, Date boeDate, String vessel, String sa, String importerName, String importerAddress1,
			String importerAddress2, String importerAddress3, String notifyParty, String notifyPartyAddress1,
			String notifyPartyAddress2, String notifyPartyAddress3, String commodityDescription,
			BigDecimal noOfPackages, BigDecimal actualNoOfPackages, String typeOfPackage, BigDecimal grossWt,
			String uom, String blNo, Date blDate, String assessiableAvailable, BigDecimal accessableValueAsValuation,
			String bidId, Date bidDate, String comments, String cvStatus, String createdBy, Date createdDate,
			String approvedBy, Date approvedDate, String status, String pol, String auctionType) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.profitcentreId = profitcentreId;
		this.noticeId = noticeId;
		this.noticeAmndNo = noticeAmndNo;
		this.finalNoticeId = finalNoticeId;
		this.noticeType = noticeType;
		this.transType = transType;
		this.noticeDate = noticeDate;
		this.igmTransId = igmTransId;
		this.igmTransDate = igmTransDate;
		this.igmNo = igmNo;
		this.igmDate = igmDate;
		this.igmLineNo = igmLineNo;
		this.viaNo = viaNo;
		this.shift = shift;
		this.source = source;
		this.boeNo = boeNo;
		this.boeDate = boeDate;
		this.vessel = vessel;
		this.sa = sa;
		this.importerName = importerName;
		this.importerAddress1 = importerAddress1;
		this.importerAddress2 = importerAddress2;
		this.importerAddress3 = importerAddress3;
		this.notifyParty = notifyParty;
		this.notifyPartyAddress1 = notifyPartyAddress1;
		this.notifyPartyAddress2 = notifyPartyAddress2;
		this.notifyPartyAddress3 = notifyPartyAddress3;
		this.commodityDescription = commodityDescription;
		this.noOfPackages = noOfPackages;
		this.actualNoOfPackages = actualNoOfPackages;
		this.typeOfPackage = typeOfPackage;
		this.grossWt = grossWt;
		this.uom = uom;
		this.blNo = blNo;
		this.blDate = blDate;
		this.assessiableAvailable = assessiableAvailable;
		this.accessableValueAsValuation = accessableValueAsValuation;
		this.bidId = bidId;
		this.bidDate = bidDate;
		this.comments = comments;
		this.cvStatus = cvStatus;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.status = status;
		this.pol = pol;
		this.auctionType = auctionType;
	}

	
	
	
	
	
	
	// to get main search data 
	public AuctionDetail(String companyId, String branchId, String noticeId, String noticeAmndNo, String finalNoticeId,
			String igmTransId, String igmNo, String igmLineNo, String blNo) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.noticeId = noticeId;
		this.noticeAmndNo = noticeAmndNo;
		this.finalNoticeId = finalNoticeId;
		this.igmTransId = igmTransId;
		this.igmNo = igmNo;
		this.igmLineNo = igmLineNo;
		this.blNo = blNo;
	}

	// Add getters and setters for all other fields...

	// toString() Method
	
	
	
	
	
	
	
	
	
	
	

}