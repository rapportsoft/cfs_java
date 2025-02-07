//package com.cwms.helper;
//
//import java.math.BigDecimal;
//import java.util.Date;
//import java.util.List;
//
//import com.fasterxml.jackson.annotation.JsonFormat;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Id;
//import jakarta.persistence.Temporal;
//import jakarta.persistence.TemporalType;
//
//public class ExportContainerAssessmentData implements Cloneable {
//		private String assesmentId;
//	    private String companyId;
//	    private String branchId;
//	    private String assesmentLineNo;
//	    private String transType;
//	    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
//	    private Date assesmentDate;
//	private String sbTransId;
//	private String sbNo;
//	 @JsonFormat(shape = JsonFormat.Shape.NUMBER)
//	private Date sbDate;
//	private String cha;
//	private String partyName;
//	private String chaGstNo;
//	private String chaAddress1;
//	private String chaAddress2;
//	private String chaAddress3;
//	private String chaState;
//	private String chaSrNo;
//	private String commodity;
//	private String profitcentreId;
//	private String profitcentreDesc;
//	private String exporterName;
//	private String exporterId;
//	private String exporterAddress1;
//	private String exporterAddress2;
//	private String exporterAddress3;
//	private String exporterState;
//	private String exporterGstNo;
//	private String exporterSrNo;
//	private String typeOfPackage;
//	private String invoiceAssessed;
//	private String assessmentId;
//	private String invoiceNo;
//	private String tanNoIdI;
//	private String tanNoIdCh;
//	private String tanNoIdFc;
//	private String gstNoCha;
//	private String address1Cha;
//	private String address2Cha;
//	private String address3Cha;
//	private String stateCha;
//	private String srNoCha;
//	private String gstNoImp;
//	private String address1Imp;
//	private String address2Imp;
//	private String address3Imp;
//	private String stateImp;
//	private String srNoImp;
//	private String gstNoCus;
//	private String address1Cus;
//	private String address2Cus;
//	private String address3Cus;
//	private String stateCus;
//	private String srNoCus;
//	private String onAccountOf;
//	private String partyNameFc;
//	private BigDecimal grossWeight;
//	private String holdStatus;
//	private String movementReqId;
//	private String newCommodity;
//	private String shippingAgent;
//	private String shippingLine;
//	private String stuffTallyWoTransId;
//	private String stuffMode;
//
//	private String cartingTransId;
//	 @JsonFormat(shape = JsonFormat.Shape.NUMBER)
//	private Date cartingDate;
//
//	private String containerNo;
//	private String containerSize;
//	private String containerType;
//	 @JsonFormat(shape = JsonFormat.Shape.NUMBER)
//	private Date stuffTallyDate;
//	private BigDecimal ContainerGrossWeight;
//	private String typeOfContainer;
//	 @JsonFormat(shape = JsonFormat.Shape.NUMBER)
//	private Date movementDate;
//	 @JsonFormat(shape = JsonFormat.Shape.NUMBER)
//	private Date gateInDate;
//	private String stuffTallyId;
//	private String cargoSbNo;
//	private BigDecimal freeDays;
//	
//	private String movementType;
//	
//	
//	
//	
//	public String getMovementType() {
//		return movementType;
//	}
//
//	public void setMovementType(String movementType) {
//		this.movementType = movementType;
//	}
//
//	public String getAssesmentId() {
//		return assesmentId;
//	}
//
//	public void setAssesmentId(String assesmentId) {
//		this.assesmentId = assesmentId;
//	}
//
//	public String getCompanyId() {
//		return companyId;
//	}
//
//	public void setCompanyId(String companyId) {
//		this.companyId = companyId;
//	}
//
//	public String getBranchId() {
//		return branchId;
//	}
//
//	public void setBranchId(String branchId) {
//		this.branchId = branchId;
//	}
//
//	public String getAssesmentLineNo() {
//		return assesmentLineNo;
//	}
//
//	public void setAssesmentLineNo(String assesmentLineNo) {
//		this.assesmentLineNo = assesmentLineNo;
//	}
//
//	public String getTransType() {
//		return transType;
//	}
//
//	public void setTransType(String transType) {
//		this.transType = transType;
//	}
//
//	public Date getAssesmentDate() {
//		return assesmentDate;
//	}
//
//	public void setAssesmentDate(Date assesmentDate) {
//		this.assesmentDate = assesmentDate;
//	}
//
//	public BigDecimal getFreeDays() {
//		return freeDays;
//	}
//
//	public void setFreeDays(BigDecimal freeDays) {
//		this.freeDays = freeDays;
//	}
//
//		public String getCargoSbNo() {
//			return cargoSbNo;
//		}
//
//		public void setCargoSbNo(String cargoSbNo) {
//			this.cargoSbNo = cargoSbNo;
//		}
//
//	
//	
//	public BigDecimal getContainerGrossWeight() {
//		return ContainerGrossWeight;
//	}
//
//	public void setContainerGrossWeight(BigDecimal containerGrossWeight) {
//		ContainerGrossWeight = containerGrossWeight;
//	}
//
//	public String getTypeOfContainer() {
//		return typeOfContainer;
//	}
//
//	public void setTypeOfContainer(String typeOfContainer) {
//		this.typeOfContainer = typeOfContainer;
//	}
//
//	public Date getMovementDate() {
//		return movementDate;
//	}
//
//	public void setMovementDate(Date movementDate) {
//		this.movementDate = movementDate;
//	}
//
//	public Date getGateInDate() {
//		return gateInDate;
//	}
//
//	public void setGateInDate(Date gateInDate) {
//		this.gateInDate = gateInDate;
//	}
//
//	public String getStuffTallyId() {
//		return stuffTallyId;
//	}
//
//	public void setStuffTallyId(String stuffTallyId) {
//		this.stuffTallyId = stuffTallyId;
//	}
//
//	private BigDecimal cargoWeight;
//	private Date gateOutDate;
//	private String ssrTransId;
//
//	private List<ssrDetail> ssrDetail;
//	private String moveMentReqId;
//	private String holdStatus2;
//	private String forceEntryFlag;
//	private String containerInvoiceType;
//	
//	 @JsonFormat(shape = JsonFormat.Shape.NUMBER)
//	private Date invoiceDate;	
//	
//	public Date getInvoiceDate() {
//		return invoiceDate;
//	}
//
//	public void setInvoiceDate(Date invoiceDate) {
//		this.invoiceDate = invoiceDate;
//	}
//
//	public static class ssrDetail {
//		private String serviceId;
//		private BigDecimal rate;
//		private String srlNo;
//		private String srNo;
//
//		public ssrDetail(String serviceId, BigDecimal rate, String srlNo, String srNo) {
//			super();
//			this.serviceId = serviceId;
//			this.rate = rate;
//			this.srlNo = srlNo;
//			this.srNo = srNo;
//		}
//
//		public String getServiceId() {
//			return serviceId;
//		}
//
//		public void setServiceId(String serviceId) {
//			this.serviceId = serviceId;
//		}
//
//		public BigDecimal getRate() {
//			return rate;
//		}
//
//		public void setRate(BigDecimal rate) {
//			this.rate = rate;
//		}
//
//		public String getSrlNo() {
//			return srlNo;
//		}
//
//		public void setSrlNo(String srlNo) {
//			this.srlNo = srlNo;
//		}
//
//		public String getSrNo() {
//			return srNo;
//		}
//
//		public void setSrNo(String srNo) {
//			this.srNo = srNo;
//		}
//		
//		
//
//	}
//
//	@Override
//	public Object clone() throws CloneNotSupportedException {
//		return super.clone();
//	}
//
//	public ExportContainerAssessmentData(String sbTransId, String sbNo, Date sbDate, String cha, String partyName,
//			String chaGstNo, String chaAddress1, String chaAddress2, String chaAddress3, String chaState,
//			String chaSrNo, String commodity, String profitcentreId, String profitcentreDesc, String exporterName,
//			String exporterId, String exporterAddress1, String exporterAddress2, String exporterAddress3,
//			String exporterState, String exporterGstNo, String exporterSrNo, String typeOfPackage,
//			String invoiceAssessed, String assessmentId, String invoiceNo, String tanNoIdI, String tanNoIdCh,
//			String tanNoIdFc, String gstNoCha, String address1Cha, String address2Cha, String address3Cha,
//			String stateCha, String srNoCha, String gstNoImp, String address1Imp, String address2Imp,
//			String address3Imp, String stateImp, String srNoImp, String gstNoCus, String address1Cus,
//			String address2Cus, String address3Cus, String stateCus, String srNoCus, String onAccountOf,
//			String partyNameFc, BigDecimal grossWeight, String holdStatus, String movementReqId, String newCommodity,
//			String shippingAgent, String shippingLine, String stuffTallyWoTransId, String stuffMode,
//			String cartingTransId, Date cartingDate, String containerNo, String containerSize, String containerType,
//			Date stuffTallyDate, BigDecimal cargoWeight, Date gateOutDate, String ssrTransId,
//			List<com.cwms.helper.ExportContainerAssessmentData.ssrDetail> ssrDetail, String moveMentReqId2,
//			String holdStatus2, String forceEntryFlag, String containerInvoiceType) {
//		super();
//		this.sbTransId = sbTransId;
//		this.sbNo = sbNo;
//		this.sbDate = sbDate;
//		this.cha = cha;
//		this.partyName = partyName;
//		this.chaGstNo = chaGstNo;
//		this.chaAddress1 = chaAddress1;
//		this.chaAddress2 = chaAddress2;
//		this.chaAddress3 = chaAddress3;
//		this.chaState = chaState;
//		this.chaSrNo = chaSrNo;
//		this.commodity = commodity;
//		this.profitcentreId = profitcentreId;
//		this.profitcentreDesc = profitcentreDesc;
//		this.exporterName = exporterName;
//		this.exporterId = exporterId;
//		this.exporterAddress1 = exporterAddress1;
//		this.exporterAddress2 = exporterAddress2;
//		this.exporterAddress3 = exporterAddress3;
//		this.exporterState = exporterState;
//		this.exporterGstNo = exporterGstNo;
//		this.exporterSrNo = exporterSrNo;
//		this.typeOfPackage = typeOfPackage;
//		this.invoiceAssessed = invoiceAssessed;
//		this.assessmentId = assessmentId;
//		this.invoiceNo = invoiceNo;
//		this.tanNoIdI = tanNoIdI;
//		this.tanNoIdCh = tanNoIdCh;
//		this.tanNoIdFc = tanNoIdFc;
//		this.gstNoCha = gstNoCha;
//		this.address1Cha = address1Cha;
//		this.address2Cha = address2Cha;
//		this.address3Cha = address3Cha;
//		this.stateCha = stateCha;
//		this.srNoCha = srNoCha;
//		this.gstNoImp = gstNoImp;
//		this.address1Imp = address1Imp;
//		this.address2Imp = address2Imp;
//		this.address3Imp = address3Imp;
//		this.stateImp = stateImp;
//		this.srNoImp = srNoImp;
//		this.gstNoCus = gstNoCus;
//		this.address1Cus = address1Cus;
//		this.address2Cus = address2Cus;
//		this.address3Cus = address3Cus;
//		this.stateCus = stateCus;
//		this.srNoCus = srNoCus;
//		this.onAccountOf = onAccountOf;
//		this.partyNameFc = partyNameFc;
//		this.grossWeight = grossWeight;
//		this.holdStatus = holdStatus;
//		this.movementReqId = movementReqId;
//		this.newCommodity = newCommodity;
//		this.shippingAgent = shippingAgent;
//		this.shippingLine = shippingLine;
//		this.stuffTallyWoTransId = stuffTallyWoTransId;
//		this.stuffMode = stuffMode;
//		this.cartingTransId = cartingTransId;
//		this.cartingDate = cartingDate;
//		this.containerNo = containerNo;
//		this.containerSize = containerSize;
//		this.containerType = containerType;
//		this.stuffTallyDate = stuffTallyDate;
//		this.cargoWeight = cargoWeight;
//		this.gateOutDate = gateOutDate;
//		this.ssrTransId = ssrTransId;
//		this.ssrDetail = ssrDetail;
//		moveMentReqId = moveMentReqId2;
//		this.holdStatus2 = holdStatus2;
//		this.forceEntryFlag = forceEntryFlag;
//		this.containerInvoiceType = containerInvoiceType;
//	}
//
//	public String getCartingTransId() {
//		return cartingTransId;
//	}
//
//	public void setCartingTransId(String cartingTransId) {
//		this.cartingTransId = cartingTransId;
//	}
//
//	public Date getCartingDate() {
//		return cartingDate;
//	}
//
//	public void setCartingDate(Date cartingDate) {
//		this.cartingDate = cartingDate;
//	}
//
//	public String getContainerNo() {
//		return containerNo;
//	}
//
//	public void setContainerNo(String containerNo) {
//		this.containerNo = containerNo;
//	}
//
//	public String getContainerSize() {
//		return containerSize;
//	}
//
//	public void setContainerSize(String containerSize) {
//		this.containerSize = containerSize;
//	}
//
//	public String getContainerType() {
//		return containerType;
//	}
//
//	public void setContainerType(String containerType) {
//		this.containerType = containerType;
//	}
//
//	public Date getStuffTallyDate() {
//		return stuffTallyDate;
//	}
//
//	public void setStuffTallyDate(Date stuffTallyDate) {
//		this.stuffTallyDate = stuffTallyDate;
//	}
//
//	public BigDecimal getCargoWeight() {
//		return cargoWeight;
//	}
//
//	public void setCargoWeight(BigDecimal cargoWeight) {
//		this.cargoWeight = cargoWeight;
//	}
//
//	public Date getGateOutDate() {
//		return gateOutDate;
//	}
//
//	public void setGateOutDate(Date gateOutDate) {
//		this.gateOutDate = gateOutDate;
//	}
//
//	public String getSsrTransId() {
//		return ssrTransId;
//	}
//
//	public void setSsrTransId(String ssrTransId) {
//		this.ssrTransId = ssrTransId;
//	}
//
//	public List<ssrDetail> getSsrDetail() {
//		return ssrDetail;
//	}
//
//	public void setSsrDetail(List<ssrDetail> ssrDetail) {
//		this.ssrDetail = ssrDetail;
//	}
//
//	public String getMoveMentReqId() {
//		return moveMentReqId;
//	}
//
//	public void setMoveMentReqId(String moveMentReqId) {
//		this.moveMentReqId = moveMentReqId;
//	}
//
//	public String getHoldStatus2() {
//		return holdStatus2;
//	}
//
//	public void setHoldStatus2(String holdStatus2) {
//		this.holdStatus2 = holdStatus2;
//	}
//
//	public String getForceEntryFlag() {
//		return forceEntryFlag;
//	}
//
//	public void setForceEntryFlag(String forceEntryFlag) {
//		this.forceEntryFlag = forceEntryFlag;
//	}
//
//	public String getContainerInvoiceType() {
//		return containerInvoiceType;
//	}
//
//	public void setContainerInvoiceType(String containerInvoiceType) {
//		this.containerInvoiceType = containerInvoiceType;
//	}
//
//	// First Query
//	public ExportContainerAssessmentData(String sbTransId, String sbNo, Date sbDate, String cha, String partyName,
//			String chaGstNo, String chaAddress1, String chaAddress2, String chaAddress3, String chaState,
//			String chaSrNo, String commodity, String profitcentreId, String profitcentreDesc, String exporterName,
//			String exporterId, String exporterAddress1, String exporterAddress2, String exporterAddress3,
//			String exporterState, String exporterGstNo, String exporterSrNo, String typeOfPackage,
//			String invoiceAssessed, String assessmentId, String invoiceNo, String tanNoIdI, String tanNoIdCh,
//			String tanNoIdFc, String gstNoCha, String address1Cha, String address2Cha, String address3Cha,
//			String stateCha, String srNoCha, String gstNoImp, String address1Imp, String address2Imp,
//			String address3Imp, String stateImp, String srNoImp, String gstNoCus, String address1Cus,
//			String address2Cus, String address3Cus, String stateCus, String srNoCus, String onAccountOf,
//			String partyNameFc, BigDecimal grossWeight, String holdStatus, String movementReqId, String newCommodity,
//			String shippingAgent, String shippingLine, String stuffTallyWoTransId, String stuffMode, Date movementDate, Date invoiceDate) {
//		super();
//		this.movementDate = movementDate;
//		this.invoiceDate = invoiceDate;
//		this.sbTransId = sbTransId;
//		this.sbNo = sbNo;
//		this.sbDate = sbDate;
//		this.cha = cha;
//		this.partyName = partyName;
//		this.chaGstNo = chaGstNo;
//		this.chaAddress1 = chaAddress1;
//		this.chaAddress2 = chaAddress2;
//		this.chaAddress3 = chaAddress3;
//		this.chaState = chaState;
//		this.chaSrNo = chaSrNo;
//		this.commodity = commodity;
//		this.profitcentreId = profitcentreId;
//		this.profitcentreDesc = profitcentreDesc;
//		this.exporterName = exporterName;
//		this.exporterId = exporterId;
//		this.exporterAddress1 = exporterAddress1;
//		this.exporterAddress2 = exporterAddress2;
//		this.exporterAddress3 = exporterAddress3;
//		this.exporterState = exporterState;
//		this.exporterGstNo = exporterGstNo;
//		this.exporterSrNo = exporterSrNo;
//		this.typeOfPackage = typeOfPackage;
//		this.invoiceAssessed = invoiceAssessed;
//		this.assessmentId = assessmentId;
//		this.invoiceNo = invoiceNo;
//		this.tanNoIdI = tanNoIdI;
//		this.tanNoIdCh = tanNoIdCh;
//		this.tanNoIdFc = tanNoIdFc;
//		this.gstNoCha = gstNoCha;
//		this.address1Cha = address1Cha;
//		this.address2Cha = address2Cha;
//		this.address3Cha = address3Cha;
//		this.stateCha = stateCha;
//		this.srNoCha = srNoCha;
//		this.gstNoImp = gstNoImp;
//		this.address1Imp = address1Imp;
//		this.address2Imp = address2Imp;
//		this.address3Imp = address3Imp;
//		this.stateImp = stateImp;
//		this.srNoImp = srNoImp;
//		this.gstNoCus = gstNoCus;
//		this.address1Cus = address1Cus;
//		this.address2Cus = address2Cus;
//		this.address3Cus = address3Cus;
//		this.stateCus = stateCus;
//		this.srNoCus = srNoCus;
//		this.onAccountOf = onAccountOf;
//		this.partyNameFc = partyNameFc;
//		this.grossWeight = grossWeight;
//		this.holdStatus = holdStatus;
//		this.movementReqId = movementReqId;
//		this.newCommodity = newCommodity;
//		this.shippingAgent = shippingAgent;
//		this.shippingLine = shippingLine;
//		this.stuffTallyWoTransId = stuffTallyWoTransId;
//		this.stuffMode = stuffMode;
//	}
//
//	public ExportContainerAssessmentData() {
//		super();
//		// TODO Auto-generated constructor stub
//	}
//
//	public String getSbTransId() {
//		return sbTransId;
//	}
//
//	public void setSbTransId(String sbTransId) {
//		this.sbTransId = sbTransId;
//	}
//
//	public String getSbNo() {
//		return sbNo;
//	}
//
//	public void setSbNo(String sbNo) {
//		this.sbNo = sbNo;
//	}
//
//	public Date getSbDate() {
//		return sbDate;
//	}
//
//	public void setSbDate(Date sbDate) {
//		this.sbDate = sbDate;
//	}
//
//	public String getCha() {
//		return cha;
//	}
//
//	public void setCha(String cha) {
//		this.cha = cha;
//	}
//
//	public String getPartyName() {
//		return partyName;
//	}
//
//	public void setPartyName(String partyName) {
//		this.partyName = partyName;
//	}
//
//	public String getChaGstNo() {
//		return chaGstNo;
//	}
//
//	public void setChaGstNo(String chaGstNo) {
//		this.chaGstNo = chaGstNo;
//	}
//
//	public String getChaAddress1() {
//		return chaAddress1;
//	}
//
//	public void setChaAddress1(String chaAddress1) {
//		this.chaAddress1 = chaAddress1;
//	}
//
//	public String getChaAddress2() {
//		return chaAddress2;
//	}
//
//	public void setChaAddress2(String chaAddress2) {
//		this.chaAddress2 = chaAddress2;
//	}
//
//	public String getChaAddress3() {
//		return chaAddress3;
//	}
//
//	public void setChaAddress3(String chaAddress3) {
//		this.chaAddress3 = chaAddress3;
//	}
//
//	public String getChaState() {
//		return chaState;
//	}
//
//	public void setChaState(String chaState) {
//		this.chaState = chaState;
//	}
//
//	public String getChaSrNo() {
//		return chaSrNo;
//	}
//
//	public void setChaSrNo(String chaSrNo) {
//		this.chaSrNo = chaSrNo;
//	}
//
//	public String getCommodity() {
//		return commodity;
//	}
//
//	public void setCommodity(String commodity) {
//		this.commodity = commodity;
//	}
//
//	public String getProfitcentreId() {
//		return profitcentreId;
//	}
//
//	public void setProfitcentreId(String profitcentreId) {
//		this.profitcentreId = profitcentreId;
//	}
//
//	public String getProfitcentreDesc() {
//		return profitcentreDesc;
//	}
//
//	public void setProfitcentreDesc(String profitcentreDesc) {
//		this.profitcentreDesc = profitcentreDesc;
//	}
//
//	public String getExporterName() {
//		return exporterName;
//	}
//
//	public void setExporterName(String exporterName) {
//		this.exporterName = exporterName;
//	}
//
//	public String getExporterId() {
//		return exporterId;
//	}
//
//	public void setExporterId(String exporterId) {
//		this.exporterId = exporterId;
//	}
//
//	public String getExporterAddress1() {
//		return exporterAddress1;
//	}
//
//	public void setExporterAddress1(String exporterAddress1) {
//		this.exporterAddress1 = exporterAddress1;
//	}
//
//	public String getExporterAddress2() {
//		return exporterAddress2;
//	}
//
//	public void setExporterAddress2(String exporterAddress2) {
//		this.exporterAddress2 = exporterAddress2;
//	}
//
//	public String getExporterAddress3() {
//		return exporterAddress3;
//	}
//
//	public void setExporterAddress3(String exporterAddress3) {
//		this.exporterAddress3 = exporterAddress3;
//	}
//
//	public String getExporterState() {
//		return exporterState;
//	}
//
//	public void setExporterState(String exporterState) {
//		this.exporterState = exporterState;
//	}
//
//	public String getExporterGstNo() {
//		return exporterGstNo;
//	}
//
//	public void setExporterGstNo(String exporterGstNo) {
//		this.exporterGstNo = exporterGstNo;
//	}
//
//	public String getExporterSrNo() {
//		return exporterSrNo;
//	}
//
//	public void setExporterSrNo(String exporterSrNo) {
//		this.exporterSrNo = exporterSrNo;
//	}
//
//	public String getTypeOfPackage() {
//		return typeOfPackage;
//	}
//
//	public void setTypeOfPackage(String typeOfPackage) {
//		this.typeOfPackage = typeOfPackage;
//	}
//
//	public String getInvoiceAssessed() {
//		return invoiceAssessed;
//	}
//
//	public void setInvoiceAssessed(String invoiceAssessed) {
//		this.invoiceAssessed = invoiceAssessed;
//	}
//
//	public String getAssessmentId() {
//		return assessmentId;
//	}
//
//	public void setAssessmentId(String assessmentId) {
//		this.assessmentId = assessmentId;
//	}
//
//	public String getInvoiceNo() {
//		return invoiceNo;
//	}
//
//	public void setInvoiceNo(String invoiceNo) {
//		this.invoiceNo = invoiceNo;
//	}
//
//	public String getTanNoIdI() {
//		return tanNoIdI;
//	}
//
//	public void setTanNoIdI(String tanNoIdI) {
//		this.tanNoIdI = tanNoIdI;
//	}
//
//	public String getTanNoIdCh() {
//		return tanNoIdCh;
//	}
//
//	public void setTanNoIdCh(String tanNoIdCh) {
//		this.tanNoIdCh = tanNoIdCh;
//	}
//
//	public String getTanNoIdFc() {
//		return tanNoIdFc;
//	}
//
//	public void setTanNoIdFc(String tanNoIdFc) {
//		this.tanNoIdFc = tanNoIdFc;
//	}
//
//	public String getGstNoCha() {
//		return gstNoCha;
//	}
//
//	public void setGstNoCha(String gstNoCha) {
//		this.gstNoCha = gstNoCha;
//	}
//
//	public String getAddress1Cha() {
//		return address1Cha;
//	}
//
//	public void setAddress1Cha(String address1Cha) {
//		this.address1Cha = address1Cha;
//	}
//
//	public String getAddress2Cha() {
//		return address2Cha;
//	}
//
//	public void setAddress2Cha(String address2Cha) {
//		this.address2Cha = address2Cha;
//	}
//
//	public String getAddress3Cha() {
//		return address3Cha;
//	}
//
//	public void setAddress3Cha(String address3Cha) {
//		this.address3Cha = address3Cha;
//	}
//
//	public String getStateCha() {
//		return stateCha;
//	}
//
//	public void setStateCha(String stateCha) {
//		this.stateCha = stateCha;
//	}
//
//	public String getSrNoCha() {
//		return srNoCha;
//	}
//
//	public void setSrNoCha(String srNoCha) {
//		this.srNoCha = srNoCha;
//	}
//
//	public String getGstNoImp() {
//		return gstNoImp;
//	}
//
//	public void setGstNoImp(String gstNoImp) {
//		this.gstNoImp = gstNoImp;
//	}
//
//	public String getAddress1Imp() {
//		return address1Imp;
//	}
//
//	public void setAddress1Imp(String address1Imp) {
//		this.address1Imp = address1Imp;
//	}
//
//	public String getAddress2Imp() {
//		return address2Imp;
//	}
//
//	public void setAddress2Imp(String address2Imp) {
//		this.address2Imp = address2Imp;
//	}
//
//	public String getAddress3Imp() {
//		return address3Imp;
//	}
//
//	public void setAddress3Imp(String address3Imp) {
//		this.address3Imp = address3Imp;
//	}
//
//	public String getStateImp() {
//		return stateImp;
//	}
//
//	public void setStateImp(String stateImp) {
//		this.stateImp = stateImp;
//	}
//
//	public String getSrNoImp() {
//		return srNoImp;
//	}
//
//	public void setSrNoImp(String srNoImp) {
//		this.srNoImp = srNoImp;
//	}
//
//	public String getGstNoCus() {
//		return gstNoCus;
//	}
//
//	public void setGstNoCus(String gstNoCus) {
//		this.gstNoCus = gstNoCus;
//	}
//
//	public String getAddress1Cus() {
//		return address1Cus;
//	}
//
//	public void setAddress1Cus(String address1Cus) {
//		this.address1Cus = address1Cus;
//	}
//
//	public String getAddress2Cus() {
//		return address2Cus;
//	}
//
//	public void setAddress2Cus(String address2Cus) {
//		this.address2Cus = address2Cus;
//	}
//
//	public String getAddress3Cus() {
//		return address3Cus;
//	}
//
//	public void setAddress3Cus(String address3Cus) {
//		this.address3Cus = address3Cus;
//	}
//
//	public String getStateCus() {
//		return stateCus;
//	}
//
//	public void setStateCus(String stateCus) {
//		this.stateCus = stateCus;
//	}
//
//	public String getSrNoCus() {
//		return srNoCus;
//	}
//
//	public void setSrNoCus(String srNoCus) {
//		this.srNoCus = srNoCus;
//	}
//
//	public String getOnAccountOf() {
//		return onAccountOf;
//	}
//
//	public void setOnAccountOf(String onAccountOf) {
//		this.onAccountOf = onAccountOf;
//	}
//
//	public String getPartyNameFc() {
//		return partyNameFc;
//	}
//
//	public void setPartyNameFc(String partyNameFc) {
//		this.partyNameFc = partyNameFc;
//	}
//
//	public BigDecimal getGrossWeight() {
//		return grossWeight;
//	}
//
//	public void setGrossWeight(BigDecimal grossWeight) {
//		this.grossWeight = grossWeight;
//	}
//
//	public String getHoldStatus() {
//		return holdStatus;
//	}
//
//	public void setHoldStatus(String holdStatus) {
//		this.holdStatus = holdStatus;
//	}
//
//	public String getMovementReqId() {
//		return movementReqId;
//	}
//
//	public void setMovementReqId(String movementReqId) {
//		this.movementReqId = movementReqId;
//	}
//
//	public String getNewCommodity() {
//		return newCommodity;
//	}
//
//	public void setNewCommodity(String newCommodity) {
//		this.newCommodity = newCommodity;
//	}
//
//	public String getShippingAgent() {
//		return shippingAgent;
//	}
//
//	public void setShippingAgent(String shippingAgent) {
//		this.shippingAgent = shippingAgent;
//	}
//
//	public String getShippingLine() {
//		return shippingLine;
//	}
//
//	public void setShippingLine(String shippingLine) {
//		this.shippingLine = shippingLine;
//	}
//
//	public String getStuffTallyWoTransId() {
//		return stuffTallyWoTransId;
//	}
//
//	public void setStuffTallyWoTransId(String stuffTallyWoTransId) {
//		this.stuffTallyWoTransId = stuffTallyWoTransId;
//	}
//
//	public String getStuffMode() {
//		return stuffMode;
//	}
//
//	public void setStuffMode(String stuffMode) {
//		this.stuffMode = stuffMode;
//	}
//
//	
//	
//	
//	
//	
//	
//	
//	
//	
//// ********************* Other Fields *********************
//	 @JsonFormat(shape = JsonFormat.Shape.NUMBER)
//	public Date destuffDate;
//	 @JsonFormat(shape = JsonFormat.Shape.NUMBER)
//	public Date gateoutDate;
//	public String examPercentage;
//	public String scannerType;
//	public String gateOutType;
//	public String checkDate;
//	public String upTariffNo;
//	public String serviceId;
//	public String serviceName;
//	public BigDecimal rates;
//	public BigDecimal grossWt;
//	public BigDecimal cargoWt;
//	 @JsonFormat(shape = JsonFormat.Shape.NUMBER)
//	public Date invoiceUptoDate;
//	 @JsonFormat(shape = JsonFormat.Shape.NUMBER)
//	public Date lastInvoiceUptoDate;
//	public String serviceUnit;
//	public String executionUnit;
//	public String serviceUnit1;
//	public String executionUnit1;
//	public String currencyId;
//	public BigDecimal discPercentage;
//	public BigDecimal discValue;
//	public BigDecimal mPercentage;
//	public BigDecimal mAmount;
//	public String woNo;
//	public String woAmndNo;
//	public String criteria;
//	public BigDecimal rangeFrom;
//	public BigDecimal rangeTo;
//	public String containerStatus;
//	public String gateOutId;
//	public String gatePassNo;
//	public String taxApp;
//	public String acCode;
//	public BigDecimal serviceRate;
//	public BigDecimal taxPerc;
//	public String taxId;
//	public BigDecimal exRate;
//	public String serviceGroup;
//	public BigDecimal area;
//	
//	
//
//	public Date getDestuffDate() {
//		return destuffDate;
//	}
//
//	public void setDestuffDate(Date destuffDate) {
//		this.destuffDate = destuffDate;
//	}
//
//	public Date getGateoutDate() {
//		return gateoutDate;
//	}
//
//	public void setGateoutDate(Date gateoutDate) {
//		this.gateoutDate = gateoutDate;
//	}
//
//	public String getExamPercentage() {
//		return examPercentage;
//	}
//
//	public void setExamPercentage(String examPercentage) {
//		this.examPercentage = examPercentage;
//	}
//
//	public String getScannerType() {
//		return scannerType;
//	}
//
//	public void setScannerType(String scannerType) {
//		this.scannerType = scannerType;
//	}
//
//	public String getGateOutType() {
//		return gateOutType;
//	}
//
//	public void setGateOutType(String gateOutType) {
//		this.gateOutType = gateOutType;
//	}
//
//	public String getCheckDate() {
//		return checkDate;
//	}
//
//	public void setCheckDate(String checkDate) {
//		this.checkDate = checkDate;
//	}
//
//	public String getUpTariffNo() {
//		return upTariffNo;
//	}
//
//	public void setUpTariffNo(String upTariffNo) {
//		this.upTariffNo = upTariffNo;
//	}
//
//	public String getServiceId() {
//		return serviceId;
//	}
//
//	public void setServiceId(String serviceId) {
//		this.serviceId = serviceId;
//	}
//
//	public String getServiceName() {
//		return serviceName;
//	}
//
//	public void setServiceName(String serviceName) {
//		this.serviceName = serviceName;
//	}
//
//	public BigDecimal getRates() {
//		return rates;
//	}
//
//	public void setRates(BigDecimal rates) {
//		this.rates = rates;
//	}
//
//	public BigDecimal getGrossWt() {
//		return grossWt;
//	}
//
//	public void setGrossWt(BigDecimal grossWt) {
//		this.grossWt = grossWt;
//	}
//
//	public BigDecimal getCargoWt() {
//		return cargoWt;
//	}
//
//	public void setCargoWt(BigDecimal cargoWt) {
//		this.cargoWt = cargoWt;
//	}
//
//	public Date getInvoiceUptoDate() {
//		return invoiceUptoDate;
//	}
//
//	public void setInvoiceUptoDate(Date invoiceUptoDate) {
//		this.invoiceUptoDate = invoiceUptoDate;
//	}
//
//	public Date getLastInvoiceUptoDate() {
//		return lastInvoiceUptoDate;
//	}
//
//	public void setLastInvoiceUptoDate(Date lastInvoiceUptoDate) {
//		this.lastInvoiceUptoDate = lastInvoiceUptoDate;
//	}
//
//	public String getServiceUnit() {
//		return serviceUnit;
//	}
//
//	public void setServiceUnit(String serviceUnit) {
//		this.serviceUnit = serviceUnit;
//	}
//
//	public String getExecutionUnit() {
//		return executionUnit;
//	}
//
//	public void setExecutionUnit(String executionUnit) {
//		this.executionUnit = executionUnit;
//	}
//
//	public String getServiceUnit1() {
//		return serviceUnit1;
//	}
//
//	public void setServiceUnit1(String serviceUnit1) {
//		this.serviceUnit1 = serviceUnit1;
//	}
//
//	public String getExecutionUnit1() {
//		return executionUnit1;
//	}
//
//	public void setExecutionUnit1(String executionUnit1) {
//		this.executionUnit1 = executionUnit1;
//	}
//
//	public String getCurrencyId() {
//		return currencyId;
//	}
//
//	public void setCurrencyId(String currencyId) {
//		this.currencyId = currencyId;
//	}
//
//	public BigDecimal getDiscPercentage() {
//		return discPercentage;
//	}
//
//	public void setDiscPercentage(BigDecimal discPercentage) {
//		this.discPercentage = discPercentage;
//	}
//
//	public BigDecimal getDiscValue() {
//		return discValue;
//	}
//
//	public void setDiscValue(BigDecimal discValue) {
//		this.discValue = discValue;
//	}
//
//	public BigDecimal getmPercentage() {
//		return mPercentage;
//	}
//
//	public void setmPercentage(BigDecimal mPercentage) {
//		this.mPercentage = mPercentage;
//	}
//
//	public BigDecimal getmAmount() {
//		return mAmount;
//	}
//
//	public void setmAmount(BigDecimal mAmount) {
//		this.mAmount = mAmount;
//	}
//
//	public String getWoNo() {
//		return woNo;
//	}
//
//	public void setWoNo(String woNo) {
//		this.woNo = woNo;
//	}
//
//	public String getWoAmndNo() {
//		return woAmndNo;
//	}
//
//	public void setWoAmndNo(String woAmndNo) {
//		this.woAmndNo = woAmndNo;
//	}
//
//	public String getCriteria() {
//		return criteria;
//	}
//
//	public void setCriteria(String criteria) {
//		this.criteria = criteria;
//	}
//
//	public BigDecimal getRangeFrom() {
//		return rangeFrom;
//	}
//
//	public void setRangeFrom(BigDecimal rangeFrom) {
//		this.rangeFrom = rangeFrom;
//	}
//
//	public BigDecimal getRangeTo() {
//		return rangeTo;
//	}
//
//	public void setRangeTo(BigDecimal rangeTo) {
//		this.rangeTo = rangeTo;
//	}
//
//	public String getContainerStatus() {
//		return containerStatus;
//	}
//
//	public void setContainerStatus(String containerStatus) {
//		this.containerStatus = containerStatus;
//	}
//
//	public String getGateOutId() {
//		return gateOutId;
//	}
//
//	public void setGateOutId(String gateOutId) {
//		this.gateOutId = gateOutId;
//	}
//
//	public String getGatePassNo() {
//		return gatePassNo;
//	}
//
//	public void setGatePassNo(String gatePassNo) {
//		this.gatePassNo = gatePassNo;
//	}
//
//	public String getTaxApp() {
//		return taxApp;
//	}
//
//	public void setTaxApp(String taxApp) {
//		this.taxApp = taxApp;
//	}
//
//	public String getAcCode() {
//		return acCode;
//	}
//
//	public void setAcCode(String acCode) {
//		this.acCode = acCode;
//	}
//
//	public BigDecimal getServiceRate() {
//		return serviceRate;
//	}
//
//	public void setServiceRate(BigDecimal serviceRate) {
//		this.serviceRate = serviceRate;
//	}
//
//	public BigDecimal getTaxPerc() {
//		return taxPerc;
//	}
//
//	public void setTaxPerc(BigDecimal taxPerc) {
//		this.taxPerc = taxPerc;
//	}
//
//	public String getTaxId() {
//		return taxId;
//	}
//
//	public void setTaxId(String taxId) {
//		this.taxId = taxId;
//	}
//
//	public BigDecimal getExRate() {
//		return exRate;
//	}
//
//	public void setExRate(BigDecimal exRate) {
//		this.exRate = exRate;
//	}
//
//	public String getServiceGroup() {
//		return serviceGroup;
//	}
//
//	public void setServiceGroup(String serviceGroup) {
//		this.serviceGroup = serviceGroup;
//	}
//
//	public BigDecimal getArea() {
//		return area;
//	}
//
//	public void setArea(BigDecimal area) {
//		this.area = area;
//	}
//
//	@Override
//	public String toString() {
//		return "ExportContainerAssessmentData [sbTransId=" + sbTransId + ", sbNo=" + sbNo + ", sbDate=" + sbDate
//				+ ", cha=" + cha + ", partyName=" + partyName + ", chaGstNo=" + chaGstNo + ", chaAddress1="
//				+ chaAddress1 + ", chaAddress2=" + chaAddress2 + ", chaAddress3=" + chaAddress3 + ", chaState="
//				+ chaState + ", chaSrNo=" + chaSrNo + ", commodity=" + commodity + ", profitcentreId=" + profitcentreId
//				+ ", profitcentreDesc=" + profitcentreDesc + ", exporterName=" + exporterName + ", exporterId="
//				+ exporterId + ", exporterAddress1=" + exporterAddress1 + ", exporterAddress2=" + exporterAddress2
//				+ ", exporterAddress3=" + exporterAddress3 + ", exporterState=" + exporterState + ", exporterGstNo="
//				+ exporterGstNo + ", exporterSrNo=" + exporterSrNo + ", typeOfPackage=" + typeOfPackage
//				+ ", invoiceAssessed=" + invoiceAssessed + ", assessmentId=" + assessmentId + ", invoiceNo=" + invoiceNo
//				+ ", tanNoIdI=" + tanNoIdI + ", tanNoIdCh=" + tanNoIdCh + ", tanNoIdFc=" + tanNoIdFc + ", gstNoCha="
//				+ gstNoCha + ", address1Cha=" + address1Cha + ", address2Cha=" + address2Cha + ", address3Cha="
//				+ address3Cha + ", stateCha=" + stateCha + ", srNoCha=" + srNoCha + ", gstNoImp=" + gstNoImp
//				+ ", address1Imp=" + address1Imp + ", address2Imp=" + address2Imp + ", address3Imp=" + address3Imp
//				+ ", stateImp=" + stateImp + ", srNoImp=" + srNoImp + ", gstNoCus=" + gstNoCus + ", address1Cus="
//				+ address1Cus + ", address2Cus=" + address2Cus + ", address3Cus=" + address3Cus + ", stateCus="
//				+ stateCus + ", srNoCus=" + srNoCus + ", onAccountOf=" + onAccountOf + ", partyNameFc=" + partyNameFc
//				+ ", grossWeight=" + grossWeight + ", holdStatus=" + holdStatus + ", movementReqId=" + movementReqId
//				+ ", newCommodity=" + newCommodity + ", shippingAgent=" + shippingAgent + ", shippingLine="
//				+ shippingLine + ", stuffTallyWoTransId=" + stuffTallyWoTransId + ", stuffMode=" + stuffMode
//				+ ", cartingTransId=" + cartingTransId + ", cartingDate=" + cartingDate + ", containerNo=" + containerNo
//				+ ", containerSize=" + containerSize + ", containerType=" + containerType + ", stuffTallyDate="
//				+ stuffTallyDate + ", ContainerGrossWeight=" + ContainerGrossWeight + ", typeOfContainer="
//				+ typeOfContainer + ", movementDate=" + movementDate + ", gateInDate=" + gateInDate + ", stuffTallyId="
//				+ stuffTallyId + ", cargoWeight=" + cargoWeight + ", gateOutDate=" + gateOutDate + ", ssrTransId="
//				+ ssrTransId + ", ssrDetail=" + ssrDetail + ", moveMentReqId=" + moveMentReqId + ", holdStatus2="
//				+ holdStatus2 + ", forceEntryFlag=" + forceEntryFlag + ", containerInvoiceType=" + containerInvoiceType
//				+ ", invoiceDate=" + invoiceDate + ", destuffDate=" + destuffDate + ", gateoutDate=" + gateoutDate
//				+ ", examPercentage=" + examPercentage + ", scannerType=" + scannerType + ", gateOutType=" + gateOutType
//				+ ", checkDate=" + checkDate + ", upTariffNo=" + upTariffNo + ", serviceId=" + serviceId
//				+ ", serviceName=" + serviceName + ", rates=" + rates + ", grossWt=" + grossWt + ", cargoWt=" + cargoWt
//				+ ", invoiceUptoDate=" + invoiceUptoDate + ", lastInvoiceUptoDate=" + lastInvoiceUptoDate
//				+ ", serviceUnit=" + serviceUnit + ", executionUnit=" + executionUnit + ", serviceUnit1=" + serviceUnit1
//				+ ", executionUnit1=" + executionUnit1 + ", currencyId=" + currencyId + ", discPercentage="
//				+ discPercentage + ", discValue=" + discValue + ", mPercentage=" + mPercentage + ", mAmount=" + mAmount
//				+ ", woNo=" + woNo + ", woAmndNo=" + woAmndNo + ", criteria=" + criteria + ", rangeFrom=" + rangeFrom
//				+ ", rangeTo=" + rangeTo + ", containerStatus=" + containerStatus + ", gateOutId=" + gateOutId
//				+ ", gatePassNo=" + gatePassNo + ", taxApp=" + taxApp + ", acCode=" + acCode + ", serviceRate="
//				+ serviceRate + ", taxPerc=" + taxPerc + ", taxId=" + taxId + ", exRate=" + exRate + ", serviceGroup="
//				+ serviceGroup + ", area=" + area + "]";
//	}
//
//	
//	
//	public ExportContainerAssessmentData(String assesmentId, String assesmentLineNo, String transType, Date assesmentDate, String containerNo, String containerSize, String containerType, 
//			Date gateInDate, Date stuffTallyDate, Date movReqDate, Date gateOutDate, Date invoiceDate, String serviceShortDesc, BigDecimal invoiceAmount ) {
//		super();
//		this.assesmentId = assesmentId;
//		this.assesmentLineNo = assesmentLineNo;
//		this.transType = transType;
//		this.assesmentDate = assesmentDate;
//		this.containerNo = containerNo;
//		this.containerSize = containerSize;
//		this.containerType = containerType;
//		this.gateInDate = gateInDate;
//		this.stuffTallyDate = stuffTallyDate;
//		this. movementDate = movReqDate;
//		this.gateOutDate = gateOutDate;
//		this.invoiceDate = invoiceDate;
//		this.serviceName = serviceShortDesc;
//		this.rates = invoiceAmount;	
//		this.checkDate = "Y";
//		
//	}
//	
//	
//	
//	public ExportContainerAssessmentData(String assesmentId, String assesmentLineNo, String transType, Date assesmentDate, String containerNo, String containerSize, String containerType, 
//			Date gateInDate, Date stuffTallyDate, Date movReqDate, Date gateOutDate, Date invoiceDate, String serviceShortDesc, BigDecimal invoiceAmount, String gatePassNo, String gateOutId, BigDecimal taxPerc) {
//		super();
//		this.assesmentId = assesmentId;
//		this.assesmentLineNo = assesmentLineNo;
//		this.transType = transType;
//		this.assesmentDate = assesmentDate;
//		this.containerNo = containerNo;
//		this.containerSize = containerSize;
//		this.containerType = containerType;
//		this.gateInDate = gateInDate;
//		this.stuffTallyDate = stuffTallyDate;
//		this. movementDate = movReqDate;
//		this.gateOutDate = gateOutDate;
//		this.invoiceDate = invoiceDate;
//		this.serviceName = serviceShortDesc;
//		this.rates = invoiceAmount;	
//		this.checkDate = "Y";
//		this.gatePassNo = gatePassNo;
//		this.gateOutId = gateOutId;
//		this.taxPerc = taxPerc;
//	}
//	
//	
//	
//	
//	
//	
//	
//	
//	
//}
























package com.cwms.helper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

public class ExportContainerAssessmentData implements Cloneable {
	private String assesmentId;
	private String companyId;
	private String branchId;
	private String assesmentLineNo;
	private String transType;
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date assesmentDate;
	private String sbTransId;
	private String commodityDescription;
	
	private String sbLineNo;
	private BigDecimal noOfPackages;
	private String cargoType;
	
	
	private String sbNo;
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date sbDate;
	private String cha;
	private String partyName;
	private String chaGstNo;
	private String chaAddress1;
	private String chaAddress2;
	private String chaAddress3;
	private String chaState;
	private String chaSrNo;
	private String commodity;
	private String profitcentreId;
	private String profitcentreDesc;
	private String exporterName;
	private String exporterId;
	private String exporterAddress1;
	private String exporterAddress2;
	private String exporterAddress3;
	private String exporterState;
	private String exporterGstNo;
	private String exporterSrNo;
	private String typeOfPackage;
	private String invoiceAssessed;
	private String assessmentId;
	private String invoiceNo;
	private String tanNoIdI;
	private String tanNoIdCh;
	private String tanNoIdFc;
	private String gstNoCha;
	private String address1Cha;
	private String address2Cha;
	private String address3Cha;
	private String stateCha;
	private String srNoCha;
	private String gstNoImp;
	private String address1Imp;
	private String address2Imp;
	private String address3Imp;
	private String stateImp;
	private String srNoImp;
	private String gstNoCus;
	private String address1Cus;
	private String address2Cus;
	private String address3Cus;
	private String stateCus;
	private String srNoCus;
	private String onAccountOf;
	private String partyNameFc;
	private BigDecimal grossWeight;
	private String holdStatus;
	private String movementReqId;
	private String newCommodity;
	private String shippingAgent;
	private String shippingLine;
	private String stuffTallyWoTransId;
	private String stuffMode;

	private String cartingTransId;
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date cartingDate;

	private String containerNo;
	private String containerSize;
	private String containerType;
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date stuffTallyDate;
	private BigDecimal ContainerGrossWeight;
	private String typeOfContainer;
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date movementDate;
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date gateInDate;
	private String stuffTallyId;
	private String cargoSbNo;
	private BigDecimal freeDays;
	
	

	public String getCommodityDescription() {
		return commodityDescription;
	}

	public void setCommodityDescription(String commodityDescription) {
		this.commodityDescription = commodityDescription;
	}

	private String movementType;

	public String getMovementType() {
		return movementType;
	}

	public void setMovementType(String movementType) {
		this.movementType = movementType;
	}
	
	public String getSbLineNo() {
		return sbLineNo;
	}

	public void setSbLineNo(String sbLineNo) {
		this.sbLineNo = sbLineNo;
	}

	public BigDecimal getNoOfPackages() {
		return noOfPackages;
	}

	public void setNoOfPackages(BigDecimal noOfPackages) {
		this.noOfPackages = noOfPackages;
	}

	public String getCargoType() {
		return cargoType;
	}

	public void setCargoType(String cargoType) {
		this.cargoType = cargoType;
	}

	public String getAssesmentId() {
		return assesmentId;
	}

	public void setAssesmentId(String assesmentId) {
		this.assesmentId = assesmentId;
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

	public String getAssesmentLineNo() {
		return assesmentLineNo;
	}

	public void setAssesmentLineNo(String assesmentLineNo) {
		this.assesmentLineNo = assesmentLineNo;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public Date getAssesmentDate() {
		return assesmentDate;
	}

	public void setAssesmentDate(Date assesmentDate) {
		this.assesmentDate = assesmentDate;
	}

	public BigDecimal getFreeDays() {
		return freeDays;
	}

	public void setFreeDays(BigDecimal freeDays) {
		this.freeDays = freeDays;
	}

	public String getCargoSbNo() {
		return cargoSbNo;
	}

	public void setCargoSbNo(String cargoSbNo) {
		this.cargoSbNo = cargoSbNo;
	}

	public BigDecimal getContainerGrossWeight() {
		return ContainerGrossWeight;
	}

	public void setContainerGrossWeight(BigDecimal containerGrossWeight) {
		ContainerGrossWeight = containerGrossWeight;
	}

	public String getTypeOfContainer() {
		return typeOfContainer;
	}

	public void setTypeOfContainer(String typeOfContainer) {
		this.typeOfContainer = typeOfContainer;
	}

	public Date getMovementDate() {
		return movementDate;
	}

	public void setMovementDate(Date movementDate) {
		this.movementDate = movementDate;
	}

	public Date getGateInDate() {
		return gateInDate;
	}

	public void setGateInDate(Date gateInDate) {
		this.gateInDate = gateInDate;
	}

	public String getStuffTallyId() {
		return stuffTallyId;
	}

	public void setStuffTallyId(String stuffTallyId) {
		this.stuffTallyId = stuffTallyId;
	}

	private BigDecimal cargoWeight;
	private Date gateOutDate;
	private String ssrTransId;

	private List<ssrDetail> ssrDetail;
	private String moveMentReqId;
	private String holdStatus2;
	private String forceEntryFlag;
	private String containerInvoiceType;

	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date invoiceDate;

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public static class ssrDetail {
		private String serviceId;
		private BigDecimal rate;
		private String srlNo;
		private String srNo;

		public ssrDetail(String serviceId, BigDecimal rate, String srlNo, String srNo) {
			super();
			this.serviceId = serviceId;
			this.rate = rate;
			this.srlNo = srlNo;
			this.srNo = srNo;
		}

		public String getServiceId() {
			return serviceId;
		}

		public void setServiceId(String serviceId) {
			this.serviceId = serviceId;
		}

		public BigDecimal getRate() {
			return rate;
		}

		public void setRate(BigDecimal rate) {
			this.rate = rate;
		}

		public String getSrlNo() {
			return srlNo;
		}

		public void setSrlNo(String srlNo) {
			this.srlNo = srlNo;
		}

		public String getSrNo() {
			return srNo;
		}

		public void setSrNo(String srNo) {
			this.srNo = srNo;
		}

	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public ExportContainerAssessmentData(String sbTransId, String sbNo, Date sbDate, String cha, String partyName,
			String chaGstNo, String chaAddress1, String chaAddress2, String chaAddress3, String chaState,
			String chaSrNo, String commodity, String profitcentreId, String profitcentreDesc, String exporterName,
			String exporterId, String exporterAddress1, String exporterAddress2, String exporterAddress3,
			String exporterState, String exporterGstNo, String exporterSrNo, String typeOfPackage,
			String invoiceAssessed, String assessmentId, String invoiceNo, String tanNoIdI, String tanNoIdCh,
			String tanNoIdFc, String gstNoCha, String address1Cha, String address2Cha, String address3Cha,
			String stateCha, String srNoCha, String gstNoImp, String address1Imp, String address2Imp,
			String address3Imp, String stateImp, String srNoImp, String gstNoCus, String address1Cus,
			String address2Cus, String address3Cus, String stateCus, String srNoCus, String onAccountOf,
			String partyNameFc, BigDecimal grossWeight, String holdStatus, String movementReqId, String newCommodity,
			String shippingAgent, String shippingLine, String stuffTallyWoTransId, String stuffMode,
			String cartingTransId, Date cartingDate, String containerNo, String containerSize, String containerType,
			Date stuffTallyDate, BigDecimal cargoWeight, Date gateOutDate, String ssrTransId,
			List<com.cwms.helper.ExportContainerAssessmentData.ssrDetail> ssrDetail, String moveMentReqId2,
			String holdStatus2, String forceEntryFlag, String containerInvoiceType) {
		super();
		this.sbTransId = sbTransId;
		this.sbNo = sbNo;
		this.sbDate = sbDate;
		this.cha = cha;
		this.partyName = partyName;
		this.chaGstNo = chaGstNo;
		this.chaAddress1 = chaAddress1;
		this.chaAddress2 = chaAddress2;
		this.chaAddress3 = chaAddress3;
		this.chaState = chaState;
		this.chaSrNo = chaSrNo;
		this.commodity = commodity;
		this.profitcentreId = profitcentreId;
		this.profitcentreDesc = profitcentreDesc;
		this.exporterName = exporterName;
		this.exporterId = exporterId;
		this.exporterAddress1 = exporterAddress1;
		this.exporterAddress2 = exporterAddress2;
		this.exporterAddress3 = exporterAddress3;
		this.exporterState = exporterState;
		this.exporterGstNo = exporterGstNo;
		this.exporterSrNo = exporterSrNo;
		this.typeOfPackage = typeOfPackage;
		this.invoiceAssessed = invoiceAssessed;
		this.assessmentId = assessmentId;
		this.invoiceNo = invoiceNo;
		this.tanNoIdI = tanNoIdI;
		this.tanNoIdCh = tanNoIdCh;
		this.tanNoIdFc = tanNoIdFc;
		this.gstNoCha = gstNoCha;
		this.address1Cha = address1Cha;
		this.address2Cha = address2Cha;
		this.address3Cha = address3Cha;
		this.stateCha = stateCha;
		this.srNoCha = srNoCha;
		this.gstNoImp = gstNoImp;
		this.address1Imp = address1Imp;
		this.address2Imp = address2Imp;
		this.address3Imp = address3Imp;
		this.stateImp = stateImp;
		this.srNoImp = srNoImp;
		this.gstNoCus = gstNoCus;
		this.address1Cus = address1Cus;
		this.address2Cus = address2Cus;
		this.address3Cus = address3Cus;
		this.stateCus = stateCus;
		this.srNoCus = srNoCus;
		this.onAccountOf = onAccountOf;
		this.partyNameFc = partyNameFc;
		this.grossWeight = grossWeight;
		this.holdStatus = holdStatus;
		this.movementReqId = movementReqId;
		this.newCommodity = newCommodity;
		this.shippingAgent = shippingAgent;
		this.shippingLine = shippingLine;
		this.stuffTallyWoTransId = stuffTallyWoTransId;
		this.stuffMode = stuffMode;
		this.cartingTransId = cartingTransId;
		this.cartingDate = cartingDate;
		this.containerNo = containerNo;
		this.containerSize = containerSize;
		this.containerType = containerType;
		this.stuffTallyDate = stuffTallyDate;
		this.cargoWeight = cargoWeight;
		this.gateOutDate = gateOutDate;
		this.ssrTransId = ssrTransId;
		this.ssrDetail = ssrDetail;
		moveMentReqId = moveMentReqId2;
		this.holdStatus2 = holdStatus2;
		this.forceEntryFlag = forceEntryFlag;
		this.containerInvoiceType = containerInvoiceType;
	}

	public String getCartingTransId() {
		return cartingTransId;
	}

	public void setCartingTransId(String cartingTransId) {
		this.cartingTransId = cartingTransId;
	}

	public Date getCartingDate() {
		return cartingDate;
	}

	public void setCartingDate(Date cartingDate) {
		this.cartingDate = cartingDate;
	}

	public String getContainerNo() {
		return containerNo;
	}

	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
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

	public Date getStuffTallyDate() {
		return stuffTallyDate;
	}

	public void setStuffTallyDate(Date stuffTallyDate) {
		this.stuffTallyDate = stuffTallyDate;
	}

	public BigDecimal getCargoWeight() {
		return cargoWeight;
	}

	public void setCargoWeight(BigDecimal cargoWeight) {
		this.cargoWeight = cargoWeight;
	}

	public Date getGateOutDate() {
		return gateOutDate;
	}

	public void setGateOutDate(Date gateOutDate) {
		this.gateOutDate = gateOutDate;
	}

	public String getSsrTransId() {
		return ssrTransId;
	}

	public void setSsrTransId(String ssrTransId) {
		this.ssrTransId = ssrTransId;
	}

	public List<ssrDetail> getSsrDetail() {
		return ssrDetail;
	}

	public void setSsrDetail(List<ssrDetail> ssrDetail) {
		this.ssrDetail = ssrDetail;
	}

	public String getMoveMentReqId() {
		return moveMentReqId;
	}

	public void setMoveMentReqId(String moveMentReqId) {
		this.moveMentReqId = moveMentReqId;
	}

	public String getHoldStatus2() {
		return holdStatus2;
	}

	public void setHoldStatus2(String holdStatus2) {
		this.holdStatus2 = holdStatus2;
	}

	public String getForceEntryFlag() {
		return forceEntryFlag;
	}

	public void setForceEntryFlag(String forceEntryFlag) {
		this.forceEntryFlag = forceEntryFlag;
	}

	public String getContainerInvoiceType() {
		return containerInvoiceType;
	}

	public void setContainerInvoiceType(String containerInvoiceType) {
		this.containerInvoiceType = containerInvoiceType;
	}

	// First Query
	public ExportContainerAssessmentData(String sbTransId, String sbNo, Date sbDate, String cha, String partyName,
			String chaGstNo, String chaAddress1, String chaAddress2, String chaAddress3, String chaState,
			String chaSrNo, String commodity, String profitcentreId, String profitcentreDesc, String exporterName,
			String exporterId, String exporterAddress1, String exporterAddress2, String exporterAddress3,
			String exporterState, String exporterGstNo, String exporterSrNo, String typeOfPackage,
			String invoiceAssessed, String assessmentId, String invoiceNo, String tanNoIdI, String tanNoIdCh,
			String tanNoIdFc, String gstNoCha, String address1Cha, String address2Cha, String address3Cha,
			String stateCha, String srNoCha, String gstNoImp, String address1Imp, String address2Imp,
			String address3Imp, String stateImp, String srNoImp, String gstNoCus, String address1Cus,
			String address2Cus, String address3Cus, String stateCus, String srNoCus, String onAccountOf,
			String partyNameFc, BigDecimal grossWeight, String holdStatus, String movementReqId, String newCommodity,
			String shippingAgent, String shippingLine, String stuffTallyWoTransId, String stuffMode, Date movementDate,
			Date invoiceDate) {
		super();
		this.movementDate = movementDate;
		this.invoiceDate = invoiceDate;
		this.sbTransId = sbTransId;
		this.sbNo = sbNo;
		this.sbDate = sbDate;
		this.cha = cha;
		this.partyName = partyName;
		this.chaGstNo = chaGstNo;
		this.chaAddress1 = chaAddress1;
		this.chaAddress2 = chaAddress2;
		this.chaAddress3 = chaAddress3;
		this.chaState = chaState;
		this.chaSrNo = chaSrNo;
		this.commodity = commodity;
		this.profitcentreId = profitcentreId;
		this.profitcentreDesc = profitcentreDesc;
		this.exporterName = exporterName;
		this.exporterId = exporterId;
		this.exporterAddress1 = exporterAddress1;
		this.exporterAddress2 = exporterAddress2;
		this.exporterAddress3 = exporterAddress3;
		this.exporterState = exporterState;
		this.exporterGstNo = exporterGstNo;
		this.exporterSrNo = exporterSrNo;
		this.typeOfPackage = typeOfPackage;
		this.invoiceAssessed = invoiceAssessed;
		this.assessmentId = assessmentId;
		this.invoiceNo = invoiceNo;
		this.tanNoIdI = tanNoIdI;
		this.tanNoIdCh = tanNoIdCh;
		this.tanNoIdFc = tanNoIdFc;
		this.gstNoCha = gstNoCha;
		this.address1Cha = address1Cha;
		this.address2Cha = address2Cha;
		this.address3Cha = address3Cha;
		this.stateCha = stateCha;
		this.srNoCha = srNoCha;
		this.gstNoImp = gstNoImp;
		this.address1Imp = address1Imp;
		this.address2Imp = address2Imp;
		this.address3Imp = address3Imp;
		this.stateImp = stateImp;
		this.srNoImp = srNoImp;
		this.gstNoCus = gstNoCus;
		this.address1Cus = address1Cus;
		this.address2Cus = address2Cus;
		this.address3Cus = address3Cus;
		this.stateCus = stateCus;
		this.srNoCus = srNoCus;
		this.onAccountOf = onAccountOf;
		this.partyNameFc = partyNameFc;
		this.grossWeight = grossWeight;
		this.holdStatus = holdStatus;
		this.movementReqId = movementReqId;
		this.newCommodity = newCommodity;
		this.shippingAgent = shippingAgent;
		this.shippingLine = shippingLine;
		this.stuffTallyWoTransId = stuffTallyWoTransId;
		this.stuffMode = stuffMode;
	}

	public ExportContainerAssessmentData() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getSbTransId() {
		return sbTransId;
	}

	public void setSbTransId(String sbTransId) {
		this.sbTransId = sbTransId;
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

	public String getCha() {
		return cha;
	}

	public void setCha(String cha) {
		this.cha = cha;
	}

	public String getPartyName() {
		return partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}

	public String getChaGstNo() {
		return chaGstNo;
	}

	public void setChaGstNo(String chaGstNo) {
		this.chaGstNo = chaGstNo;
	}

	public String getChaAddress1() {
		return chaAddress1;
	}

	public void setChaAddress1(String chaAddress1) {
		this.chaAddress1 = chaAddress1;
	}

	public String getChaAddress2() {
		return chaAddress2;
	}

	public void setChaAddress2(String chaAddress2) {
		this.chaAddress2 = chaAddress2;
	}

	public String getChaAddress3() {
		return chaAddress3;
	}

	public void setChaAddress3(String chaAddress3) {
		this.chaAddress3 = chaAddress3;
	}

	public String getChaState() {
		return chaState;
	}

	public void setChaState(String chaState) {
		this.chaState = chaState;
	}

	public String getChaSrNo() {
		return chaSrNo;
	}

	public void setChaSrNo(String chaSrNo) {
		this.chaSrNo = chaSrNo;
	}

	public String getCommodity() {
		return commodity;
	}

	public void setCommodity(String commodity) {
		this.commodity = commodity;
	}

	public String getProfitcentreId() {
		return profitcentreId;
	}

	public void setProfitcentreId(String profitcentreId) {
		this.profitcentreId = profitcentreId;
	}

	public String getProfitcentreDesc() {
		return profitcentreDesc;
	}

	public void setProfitcentreDesc(String profitcentreDesc) {
		this.profitcentreDesc = profitcentreDesc;
	}

	public String getExporterName() {
		return exporterName;
	}

	public void setExporterName(String exporterName) {
		this.exporterName = exporterName;
	}

	public String getExporterId() {
		return exporterId;
	}

	public void setExporterId(String exporterId) {
		this.exporterId = exporterId;
	}

	public String getExporterAddress1() {
		return exporterAddress1;
	}

	public void setExporterAddress1(String exporterAddress1) {
		this.exporterAddress1 = exporterAddress1;
	}

	public String getExporterAddress2() {
		return exporterAddress2;
	}

	public void setExporterAddress2(String exporterAddress2) {
		this.exporterAddress2 = exporterAddress2;
	}

	public String getExporterAddress3() {
		return exporterAddress3;
	}

	public void setExporterAddress3(String exporterAddress3) {
		this.exporterAddress3 = exporterAddress3;
	}

	public String getExporterState() {
		return exporterState;
	}

	public void setExporterState(String exporterState) {
		this.exporterState = exporterState;
	}

	public String getExporterGstNo() {
		return exporterGstNo;
	}

	public void setExporterGstNo(String exporterGstNo) {
		this.exporterGstNo = exporterGstNo;
	}

	public String getExporterSrNo() {
		return exporterSrNo;
	}

	public void setExporterSrNo(String exporterSrNo) {
		this.exporterSrNo = exporterSrNo;
	}

	public String getTypeOfPackage() {
		return typeOfPackage;
	}

	public void setTypeOfPackage(String typeOfPackage) {
		this.typeOfPackage = typeOfPackage;
	}

	public String getInvoiceAssessed() {
		return invoiceAssessed;
	}

	public void setInvoiceAssessed(String invoiceAssessed) {
		this.invoiceAssessed = invoiceAssessed;
	}

	public String getAssessmentId() {
		return assessmentId;
	}

	public void setAssessmentId(String assessmentId) {
		this.assessmentId = assessmentId;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getTanNoIdI() {
		return tanNoIdI;
	}

	public void setTanNoIdI(String tanNoIdI) {
		this.tanNoIdI = tanNoIdI;
	}

	public String getTanNoIdCh() {
		return tanNoIdCh;
	}

	public void setTanNoIdCh(String tanNoIdCh) {
		this.tanNoIdCh = tanNoIdCh;
	}

	public String getTanNoIdFc() {
		return tanNoIdFc;
	}

	public void setTanNoIdFc(String tanNoIdFc) {
		this.tanNoIdFc = tanNoIdFc;
	}

	public String getGstNoCha() {
		return gstNoCha;
	}

	public void setGstNoCha(String gstNoCha) {
		this.gstNoCha = gstNoCha;
	}

	public String getAddress1Cha() {
		return address1Cha;
	}

	public void setAddress1Cha(String address1Cha) {
		this.address1Cha = address1Cha;
	}

	public String getAddress2Cha() {
		return address2Cha;
	}

	public void setAddress2Cha(String address2Cha) {
		this.address2Cha = address2Cha;
	}

	public String getAddress3Cha() {
		return address3Cha;
	}

	public void setAddress3Cha(String address3Cha) {
		this.address3Cha = address3Cha;
	}

	public String getStateCha() {
		return stateCha;
	}

	public void setStateCha(String stateCha) {
		this.stateCha = stateCha;
	}

	public String getSrNoCha() {
		return srNoCha;
	}

	public void setSrNoCha(String srNoCha) {
		this.srNoCha = srNoCha;
	}

	public String getGstNoImp() {
		return gstNoImp;
	}

	public void setGstNoImp(String gstNoImp) {
		this.gstNoImp = gstNoImp;
	}

	public String getAddress1Imp() {
		return address1Imp;
	}

	public void setAddress1Imp(String address1Imp) {
		this.address1Imp = address1Imp;
	}

	public String getAddress2Imp() {
		return address2Imp;
	}

	public void setAddress2Imp(String address2Imp) {
		this.address2Imp = address2Imp;
	}

	public String getAddress3Imp() {
		return address3Imp;
	}

	public void setAddress3Imp(String address3Imp) {
		this.address3Imp = address3Imp;
	}

	public String getStateImp() {
		return stateImp;
	}

	public void setStateImp(String stateImp) {
		this.stateImp = stateImp;
	}

	public String getSrNoImp() {
		return srNoImp;
	}

	public void setSrNoImp(String srNoImp) {
		this.srNoImp = srNoImp;
	}

	public String getGstNoCus() {
		return gstNoCus;
	}

	public void setGstNoCus(String gstNoCus) {
		this.gstNoCus = gstNoCus;
	}

	public String getAddress1Cus() {
		return address1Cus;
	}

	public void setAddress1Cus(String address1Cus) {
		this.address1Cus = address1Cus;
	}

	public String getAddress2Cus() {
		return address2Cus;
	}

	public void setAddress2Cus(String address2Cus) {
		this.address2Cus = address2Cus;
	}

	public String getAddress3Cus() {
		return address3Cus;
	}

	public void setAddress3Cus(String address3Cus) {
		this.address3Cus = address3Cus;
	}

	public String getStateCus() {
		return stateCus;
	}

	public void setStateCus(String stateCus) {
		this.stateCus = stateCus;
	}

	public String getSrNoCus() {
		return srNoCus;
	}

	public void setSrNoCus(String srNoCus) {
		this.srNoCus = srNoCus;
	}

	public String getOnAccountOf() {
		return onAccountOf;
	}

	public void setOnAccountOf(String onAccountOf) {
		this.onAccountOf = onAccountOf;
	}

	public String getPartyNameFc() {
		return partyNameFc;
	}

	public void setPartyNameFc(String partyNameFc) {
		this.partyNameFc = partyNameFc;
	}

	public BigDecimal getGrossWeight() {
		return grossWeight;
	}

	public void setGrossWeight(BigDecimal grossWeight) {
		this.grossWeight = grossWeight;
	}

	public String getHoldStatus() {
		return holdStatus;
	}

	public void setHoldStatus(String holdStatus) {
		this.holdStatus = holdStatus;
	}

	public String getMovementReqId() {
		return movementReqId;
	}

	public void setMovementReqId(String movementReqId) {
		this.movementReqId = movementReqId;
	}

	public String getNewCommodity() {
		return newCommodity;
	}

	public void setNewCommodity(String newCommodity) {
		this.newCommodity = newCommodity;
	}

	public String getShippingAgent() {
		return shippingAgent;
	}

	public void setShippingAgent(String shippingAgent) {
		this.shippingAgent = shippingAgent;
	}

	public String getShippingLine() {
		return shippingLine;
	}

	public void setShippingLine(String shippingLine) {
		this.shippingLine = shippingLine;
	}

	public String getStuffTallyWoTransId() {
		return stuffTallyWoTransId;
	}

	public void setStuffTallyWoTransId(String stuffTallyWoTransId) {
		this.stuffTallyWoTransId = stuffTallyWoTransId;
	}

	public String getStuffMode() {
		return stuffMode;
	}

	public void setStuffMode(String stuffMode) {
		this.stuffMode = stuffMode;
	}

// ********************* Other Fields *********************
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	public Date destuffDate;
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	public Date gateoutDate;
	public String examPercentage;
	public String scannerType;
	public String gateOutType;
	public String checkDate;
	public String upTariffNo;
	public String serviceId;
	public String serviceName;
	public BigDecimal rates;
	public BigDecimal grossWt;
	public BigDecimal cargoWt;
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	public Date invoiceUptoDate;
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	public Date lastInvoiceUptoDate;
	public String serviceUnit;
	public String executionUnit;
	public String serviceUnit1;
	public String executionUnit1;
	public String currencyId;
	public BigDecimal discPercentage;
	public BigDecimal discValue;
	public BigDecimal mPercentage;
	public BigDecimal mAmount;
	public String woNo;
	public String woAmndNo;
	public String criteria;
	public BigDecimal rangeFrom;
	public BigDecimal rangeTo;
	public String containerStatus;
	public String gateOutId;
	public String gatePassNo;
	public String taxApp;
	public String acCode;
	public BigDecimal serviceRate;
	public BigDecimal taxPerc;
	public String taxId;
	public BigDecimal exRate;
	public String serviceGroup;
	public BigDecimal area;

	public Date getDestuffDate() {
		return destuffDate;
	}

	public void setDestuffDate(Date destuffDate) {
		this.destuffDate = destuffDate;
	}

	public Date getGateoutDate() {
		return gateoutDate;
	}

	public void setGateoutDate(Date gateoutDate) {
		this.gateoutDate = gateoutDate;
	}

	public String getExamPercentage() {
		return examPercentage;
	}

	public void setExamPercentage(String examPercentage) {
		this.examPercentage = examPercentage;
	}

	public String getScannerType() {
		return scannerType;
	}

	public void setScannerType(String scannerType) {
		this.scannerType = scannerType;
	}

	public String getGateOutType() {
		return gateOutType;
	}

	public void setGateOutType(String gateOutType) {
		this.gateOutType = gateOutType;
	}

	public String getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}

	public String getUpTariffNo() {
		return upTariffNo;
	}

	public void setUpTariffNo(String upTariffNo) {
		this.upTariffNo = upTariffNo;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public BigDecimal getRates() {
		return rates;
	}

	public void setRates(BigDecimal rates) {
		this.rates = rates;
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

	public Date getInvoiceUptoDate() {
		return invoiceUptoDate;
	}

	public void setInvoiceUptoDate(Date invoiceUptoDate) {
		this.invoiceUptoDate = invoiceUptoDate;
	}

	public Date getLastInvoiceUptoDate() {
		return lastInvoiceUptoDate;
	}

	public void setLastInvoiceUptoDate(Date lastInvoiceUptoDate) {
		this.lastInvoiceUptoDate = lastInvoiceUptoDate;
	}

	public String getServiceUnit() {
		return serviceUnit;
	}

	public void setServiceUnit(String serviceUnit) {
		this.serviceUnit = serviceUnit;
	}

	public String getExecutionUnit() {
		return executionUnit;
	}

	public void setExecutionUnit(String executionUnit) {
		this.executionUnit = executionUnit;
	}

	public String getServiceUnit1() {
		return serviceUnit1;
	}

	public void setServiceUnit1(String serviceUnit1) {
		this.serviceUnit1 = serviceUnit1;
	}

	public String getExecutionUnit1() {
		return executionUnit1;
	}

	public void setExecutionUnit1(String executionUnit1) {
		this.executionUnit1 = executionUnit1;
	}

	public String getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}

	public BigDecimal getDiscPercentage() {
		return discPercentage;
	}

	public void setDiscPercentage(BigDecimal discPercentage) {
		this.discPercentage = discPercentage;
	}

	public BigDecimal getDiscValue() {
		return discValue;
	}

	public void setDiscValue(BigDecimal discValue) {
		this.discValue = discValue;
	}

	public BigDecimal getmPercentage() {
		return mPercentage;
	}

	public void setmPercentage(BigDecimal mPercentage) {
		this.mPercentage = mPercentage;
	}

	public BigDecimal getmAmount() {
		return mAmount;
	}

	public void setmAmount(BigDecimal mAmount) {
		this.mAmount = mAmount;
	}

	public String getWoNo() {
		return woNo;
	}

	public void setWoNo(String woNo) {
		this.woNo = woNo;
	}

	public String getWoAmndNo() {
		return woAmndNo;
	}

	public void setWoAmndNo(String woAmndNo) {
		this.woAmndNo = woAmndNo;
	}

	public String getCriteria() {
		return criteria;
	}

	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}

	public BigDecimal getRangeFrom() {
		return rangeFrom;
	}

	public void setRangeFrom(BigDecimal rangeFrom) {
		this.rangeFrom = rangeFrom;
	}

	public BigDecimal getRangeTo() {
		return rangeTo;
	}

	public void setRangeTo(BigDecimal rangeTo) {
		this.rangeTo = rangeTo;
	}

	public String getContainerStatus() {
		return containerStatus;
	}

	public void setContainerStatus(String containerStatus) {
		this.containerStatus = containerStatus;
	}

	public String getGateOutId() {
		return gateOutId;
	}

	public void setGateOutId(String gateOutId) {
		this.gateOutId = gateOutId;
	}

	public String getGatePassNo() {
		return gatePassNo;
	}

	public void setGatePassNo(String gatePassNo) {
		this.gatePassNo = gatePassNo;
	}

	public String getTaxApp() {
		return taxApp;
	}

	public void setTaxApp(String taxApp) {
		this.taxApp = taxApp;
	}

	public String getAcCode() {
		return acCode;
	}

	public void setAcCode(String acCode) {
		this.acCode = acCode;
	}

	public BigDecimal getServiceRate() {
		return serviceRate;
	}

	public void setServiceRate(BigDecimal serviceRate) {
		this.serviceRate = serviceRate;
	}

	public BigDecimal getTaxPerc() {
		return taxPerc;
	}

	public void setTaxPerc(BigDecimal taxPerc) {
		this.taxPerc = taxPerc;
	}

	public String getTaxId() {
		return taxId;
	}

	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}

	public BigDecimal getExRate() {
		return exRate;
	}

	public void setExRate(BigDecimal exRate) {
		this.exRate = exRate;
	}

	public String getServiceGroup() {
		return serviceGroup;
	}

	public void setServiceGroup(String serviceGroup) {
		this.serviceGroup = serviceGroup;
	}

	public BigDecimal getArea() {
		return area;
	}

	public void setArea(BigDecimal area) {
		this.area = area;
	}

	@Override
	public String toString() {
		return "ExportContainerAssessmentData [sbTransId=" + sbTransId + ", sbNo=" + sbNo + ", sbDate=" + sbDate
				+ ", cha=" + cha + ", partyName=" + partyName + ", chaGstNo=" + chaGstNo + ", chaAddress1="
				+ chaAddress1 + ", chaAddress2=" + chaAddress2 + ", chaAddress3=" + chaAddress3 + ", chaState="
				+ chaState + ", chaSrNo=" + chaSrNo + ", commodity=" + commodity + ", profitcentreId=" + profitcentreId
				+ ", profitcentreDesc=" + profitcentreDesc + ", exporterName=" + exporterName + ", exporterId="
				+ exporterId + ", exporterAddress1=" + exporterAddress1 + ", exporterAddress2=" + exporterAddress2
				+ ", exporterAddress3=" + exporterAddress3 + ", exporterState=" + exporterState + ", exporterGstNo="
				+ exporterGstNo + ", exporterSrNo=" + exporterSrNo + ", typeOfPackage=" + typeOfPackage
				+ ", invoiceAssessed=" + invoiceAssessed + ", assessmentId=" + assessmentId + ", invoiceNo=" + invoiceNo
				+ ", tanNoIdI=" + tanNoIdI + ", tanNoIdCh=" + tanNoIdCh + ", tanNoIdFc=" + tanNoIdFc + ", gstNoCha="
				+ gstNoCha + ", address1Cha=" + address1Cha + ", address2Cha=" + address2Cha + ", address3Cha="
				+ address3Cha + ", stateCha=" + stateCha + ", srNoCha=" + srNoCha + ", gstNoImp=" + gstNoImp
				+ ", address1Imp=" + address1Imp + ", address2Imp=" + address2Imp + ", address3Imp=" + address3Imp
				+ ", stateImp=" + stateImp + ", srNoImp=" + srNoImp + ", gstNoCus=" + gstNoCus + ", address1Cus="
				+ address1Cus + ", address2Cus=" + address2Cus + ", address3Cus=" + address3Cus + ", stateCus="
				+ stateCus + ", srNoCus=" + srNoCus + ", onAccountOf=" + onAccountOf + ", partyNameFc=" + partyNameFc
				+ ", grossWeight=" + grossWeight + ", holdStatus=" + holdStatus + ", movementReqId=" + movementReqId
				+ ", newCommodity=" + newCommodity + ", shippingAgent=" + shippingAgent + ", shippingLine="
				+ shippingLine + ", stuffTallyWoTransId=" + stuffTallyWoTransId + ", stuffMode=" + stuffMode
				+ ", cartingTransId=" + cartingTransId + ", cartingDate=" + cartingDate + ", containerNo=" + containerNo
				+ ", containerSize=" + containerSize + ", containerType=" + containerType + ", stuffTallyDate="
				+ stuffTallyDate + ", ContainerGrossWeight=" + ContainerGrossWeight + ", typeOfContainer="
				+ typeOfContainer + ", movementDate=" + movementDate + ", gateInDate=" + gateInDate + ", stuffTallyId="
				+ stuffTallyId + ", cargoWeight=" + cargoWeight + ", gateOutDate=" + gateOutDate + ", ssrTransId="
				+ ssrTransId + ", ssrDetail=" + ssrDetail + ", moveMentReqId=" + moveMentReqId + ", holdStatus2="
				+ holdStatus2 + ", forceEntryFlag=" + forceEntryFlag + ", containerInvoiceType=" + containerInvoiceType
				+ ", invoiceDate=" + invoiceDate + ", destuffDate=" + destuffDate + ", gateoutDate=" + gateoutDate
				+ ", examPercentage=" + examPercentage + ", scannerType=" + scannerType + ", gateOutType=" + gateOutType
				+ ", checkDate=" + checkDate + ", upTariffNo=" + upTariffNo + ", serviceId=" + serviceId
				+ ", serviceName=" + serviceName + ", rates=" + rates + ", grossWt=" + grossWt + ", cargoWt=" + cargoWt
				+ ", invoiceUptoDate=" + invoiceUptoDate + ", lastInvoiceUptoDate=" + lastInvoiceUptoDate
				+ ", serviceUnit=" + serviceUnit + ", executionUnit=" + executionUnit + ", serviceUnit1=" + serviceUnit1
				+ ", executionUnit1=" + executionUnit1 + ", currencyId=" + currencyId + ", discPercentage="
				+ discPercentage + ", discValue=" + discValue + ", mPercentage=" + mPercentage + ", mAmount=" + mAmount
				+ ", woNo=" + woNo + ", woAmndNo=" + woAmndNo + ", criteria=" + criteria + ", rangeFrom=" + rangeFrom
				+ ", rangeTo=" + rangeTo + ", containerStatus=" + containerStatus + ", gateOutId=" + gateOutId
				+ ", gatePassNo=" + gatePassNo + ", taxApp=" + taxApp + ", acCode=" + acCode + ", serviceRate="
				+ serviceRate + ", taxPerc=" + taxPerc + ", taxId=" + taxId + ", exRate=" + exRate + ", serviceGroup="
				+ serviceGroup + ", area=" + area + "]";
	}

	public ExportContainerAssessmentData(String assesmentId, String assesmentLineNo, String transType,
			Date assesmentDate, String containerNo, String containerSize, String containerType, Date gateInDate,
			Date stuffTallyDate, Date movReqDate, Date gateOutDate, Date invoiceDate, String serviceShortDesc,
			BigDecimal invoiceAmount) {
		super();
		this.assesmentId = assesmentId;
		this.assesmentLineNo = assesmentLineNo;
		this.transType = transType;
		this.assesmentDate = assesmentDate;
		this.containerNo = containerNo;
		this.containerSize = containerSize;
		this.containerType = containerType;
		this.gateInDate = gateInDate;
		this.stuffTallyDate = stuffTallyDate;
		this.movementDate = movReqDate;
		this.gateOutDate = gateOutDate;
		this.invoiceDate = invoiceDate;
		this.serviceName = serviceShortDesc;
		this.rates = invoiceAmount;
		this.checkDate = "Y";

	}

	public ExportContainerAssessmentData(String assesmentId, String assesmentLineNo, String transType,
			Date assesmentDate, String containerNo, String containerSize, String containerType, Date gateInDate,
			Date stuffTallyDate, Date movReqDate, Date gateOutDate, Date invoiceDate, String serviceShortDesc,
			BigDecimal invoiceAmount, String gatePassNo, String gateOutId, BigDecimal taxPerc) {
		super();
		this.assesmentId = assesmentId;
		this.assesmentLineNo = assesmentLineNo;
		this.transType = transType;
		this.assesmentDate = assesmentDate;
		this.containerNo = containerNo;
		this.containerSize = containerSize;
		this.containerType = containerType;
		this.gateInDate = gateInDate;
		this.stuffTallyDate = stuffTallyDate;
		this.movementDate = movReqDate;
		this.gateOutDate = gateOutDate;
		this.invoiceDate = invoiceDate;
		this.serviceName = serviceShortDesc;
		this.rates = invoiceAmount;
		this.checkDate = "Y";
		this.gatePassNo = gatePassNo;
		this.gateOutId = gateOutId;
		this.taxPerc = taxPerc;
	}

	private String crgInvoiceNo;
	
	
	public String getCrgInvoiceNo() {
		return crgInvoiceNo;
	}

	public void setCrgInvoiceNo(String crgInvoiceNo) {
		this.crgInvoiceNo = crgInvoiceNo;
	}

	public ExportContainerAssessmentData(String sbTransId, String sbNo, Date sbDate, String cha, String partyName,
			String gstNo, String address1, String address2, String address3, String state, String srNo, String tanNoId,
			String exporterId, String exporterName, String impGstNo, String impAddress1, String impAddress2,
			String impAddress3, String impState, String impSrNo, String importerTanNoId, String onAccountOf,
			String chPartyName, String chaGstNo, String chaAddress1, String chaAddress2, String chaAddress3,
			String chaState, String chaSrNo, String chTanNoId, String commodity, String profitcentreId,
			String profitcentreDesc, String typeOfPackage, String invoiceAssessed, String assesmentId,
			String crgInvoiceNo, BigDecimal grossWeight, String newCommodity, String holdStatus) {
		this.sbTransId = sbTransId;
		this.sbNo = sbNo;
		this.sbDate = sbDate;
		this.cha = cha;
		this.partyName = partyName;
		this.chaGstNo = gstNo;
		this.chaAddress1 = address1;
		this.chaAddress2 = address2;
		this.chaAddress3 = address3;
		this.chaState = state;
		this.chaSrNo = srNo;
		this.tanNoIdCh = tanNoId;
		this.exporterId = exporterId;
		this.exporterName = exporterName;
		this.exporterGstNo = impGstNo;
		this.exporterAddress1 = impAddress1;
		this.exporterAddress2 = impAddress2;
		this.exporterAddress3 = impAddress3;
		this.exporterState = impState;
		this.exporterSrNo = impSrNo;
		this.tanNoIdI = importerTanNoId;
		this.onAccountOf = onAccountOf;
		this.partyNameFc = chPartyName;
		this.chaGstNo = chaGstNo;
		this.chaAddress1 = chaAddress1;
		this.chaAddress2 = chaAddress2;
		this.chaAddress3 = chaAddress3;
		this.chaState = chaState;
		this.chaSrNo = chaSrNo;
		this.tanNoIdFc = chTanNoId;
		this.commodity = commodity;
		this.profitcentreId = profitcentreId;
		this.profitcentreDesc = profitcentreDesc;
		this.typeOfPackage = typeOfPackage;
		this.invoiceAssessed = invoiceAssessed;
		this.assesmentId = assesmentId;
		this.crgInvoiceNo = crgInvoiceNo;
		this.grossWeight = grossWeight;
		this.newCommodity = newCommodity;
		this.holdStatus = holdStatus;
	}

	
	
	
	
	
//	Export Carting
	public ExportContainerAssessmentData(String assesmentId, String assesmentLineNo, String transType, Date assesmentDate, String commodityDescription, String commodityCode,
			BigDecimal noOfPackages, String typeOfCargo, String serviceName, BigDecimal rates, BigDecimal grossWeight) {
		this.assesmentId = assesmentId;
		this.assesmentLineNo = assesmentLineNo;
		this.transType = transType;
		this.assesmentDate = assesmentDate;
		this.commodityDescription = commodityDescription;
		this.commodity = commodityCode;
		this.newCommodity = commodityCode;
		this.noOfPackages = noOfPackages;
		this.cargoType = typeOfCargo;
		this.serviceName = serviceName;
		this.rates = rates;
		this.grossWeight = 	grossWeight;
		this.checkDate = "Y";
	}
	
//	Export BackTo Town
	
	
	private BigDecimal backToTownWeight;
	private BigDecimal areaUsed;
	private String backToTownTransId;
	private String gridLocation;
		
	public BigDecimal getBackToTownWeight() {
		return backToTownWeight;
	}

	public void setBackToTownWeight(BigDecimal backToTownWeight) {
		this.backToTownWeight = backToTownWeight;
	}

	public BigDecimal getAreaUsed() {
		return areaUsed;
	}

	public void setAreaUsed(BigDecimal areaOccupied) {
		this.areaUsed = areaOccupied;
	}

	public String getBackToTownTransId() {
		return backToTownTransId;
	}

	public void setBackToTownTransId(String backToTownTransId) {
		this.backToTownTransId = backToTownTransId;
	}

	public String getGridLocation() {
		return gridLocation;
	}

	public void setGridLocation(String gridLocation) {
		this.gridLocation = gridLocation;
	}

	public ExportContainerAssessmentData(String sbTransId, String sbNo, Date sbDate, String commodity, String profitcentreId, String profitcentreDesc, String typeOfPackage, String invoiceAssesed, String assesmentId, String invoiceNo,
			String exporterId, String exporterName, String impAddress1, String impAddress2, String impAddress3, String impState, String impGstNo, String impSrNo, String importerTanNoId, String onAccountOf, 
			String onPartyName, String onAddress1, String onAddress2, String onAddress3, String onState, String onGstNo, String onSrNo, String onTanNoId, String cha, String partyName,
			String address1, String address2, String address3, String state, String gstNo, String srNo, String tanNoId,	Date cartingTransDate, BigDecimal backToTownWeight, BigDecimal areaOccupied, 
			String backToTownTransId,String newCommodity, BigDecimal grossWeight, String typeOfContainer, String ssrTransId, String holdStatus, String gridLocation, BigDecimal cargoWeight) {
		this.sbTransId = sbTransId;
		this.sbNo = sbNo;
		this.sbDate = sbDate;
		this.cha = cha;
		this.partyName = partyName;
		this.chaGstNo = gstNo;
		this.chaAddress1 = address1;
		this.chaAddress2 = address2;
		this.chaAddress3 = address3;
		this.chaState = state;
		this.chaSrNo = srNo;
		this.tanNoIdCh = tanNoId;
		this.exporterId = exporterId;
		this.exporterName = exporterName;
		this.exporterGstNo = impGstNo;
		this.exporterAddress1 = impAddress1;
		this.exporterAddress2 = impAddress2;
		this.exporterAddress3 = impAddress3;
		this.exporterState = impState;
		this.exporterSrNo = impSrNo;
		this.tanNoIdI = importerTanNoId;
		this.onAccountOf = onAccountOf;
		this.partyNameFc = onPartyName;
		this.gstNoCus = onGstNo;
		this.address1Cus = onAddress1;
		this.address2Cus = onAddress2;
		this.address3Cus = onAddress3;
		this.stateCus = onState;
		this.srNoCus = onSrNo;
		this.tanNoIdFc = onTanNoId;
		this.commodity = commodity;
		this.profitcentreId = profitcentreId;
		this.profitcentreDesc = profitcentreDesc;
		this.typeOfPackage = typeOfPackage;
		this.invoiceAssessed = invoiceAssesed;
		this.assesmentId = assesmentId;
		this.invoiceNo = invoiceNo;
		this.grossWeight = backToTownWeight;
		this.newCommodity = newCommodity;
		this.holdStatus = holdStatus;
		this.cartingDate = cartingTransDate;
		this.backToTownWeight = backToTownWeight;
		this.areaUsed = areaOccupied;
		this.backToTownTransId = backToTownTransId;
		this.typeOfContainer = typeOfContainer;
		this.ssrTransId = ssrTransId;
		this.gridLocation = gridLocation;		
		this.cargoWeight = grossWeight;
	}
	
	
//	Export Back To Town
	public ExportContainerAssessmentData(String assesmentId, String assesmentLineNo, String transType, Date assesmentDate, String commodityDescription,
			Date cartingDate, BigDecimal grossWeight, BigDecimal areaUsed, Date invoiceDate, String serviceName, BigDecimal rates, BigDecimal taxPer) {
		this.assesmentId = assesmentId;
		this.assesmentLineNo = assesmentLineNo;
		this.transType = transType;
		this.assesmentDate = assesmentDate;
		this.commodity = commodityDescription;
		this.serviceName = serviceName;
		this.rates = rates;
		this.invoiceDate = invoiceDate;
		this.areaUsed = areaUsed;
		this.backToTownWeight = grossWeight;
		this.cartingDate = cartingDate;
		this.checkDate = "Y";
		this.taxPerc = taxPer;
	}
	
	public ExportContainerAssessmentData(String assesmentId, String assesmentLineNo, Date assesmentDate, String sbNo, String sbTransId, String commodityDescription,
			Date cartingDate, BigDecimal grossWeight, BigDecimal areaUsed) {
		this.assesmentId = assesmentId;
		this.assesmentLineNo = assesmentLineNo;
		this.assesmentDate = assesmentDate;
		this.commodity = commodityDescription;
		this.areaUsed = areaUsed;
		this.cartingDate = cartingDate;
		this.sbNo = sbNo;
		this.sbTransId= sbTransId;
		this.grossWeight = grossWeight;	
	}
	
	public ExportContainerAssessmentData(String assesmentId, String assesmentLineNo, Date assesmentDate, String sbNo, String sbTransId, String commodityDescription,
			Date cartingDate, BigDecimal grossWeight, BigDecimal areaUsed, BigDecimal taxPer) {
		this.assesmentId = assesmentId;
		this.assesmentLineNo = assesmentLineNo;
		this.assesmentDate = assesmentDate;
		this.commodity = commodityDescription;
		this.areaUsed = areaUsed;
		this.cartingDate = cartingDate;
		this.sbNo = sbNo;
		this.sbTransId= sbTransId;
		this.grossWeight = grossWeight;	
		this.taxPerc = taxPer;
	}
	
	public ExportContainerAssessmentData(String assesmentId, String assesmentLineNo, String transType, Date assesmentDate, String commodityDescription, String commodityCode,
			BigDecimal noOfPackages, String typeOfCargo, String serviceName, BigDecimal rates, BigDecimal grossWeight, BigDecimal taxPer) {
		this.assesmentId = assesmentId;
		this.assesmentLineNo = assesmentLineNo;
		this.transType = transType;
		this.assesmentDate = assesmentDate;
		this.commodityDescription = commodityDescription;
		this.commodity = commodityCode;
		this.newCommodity = commodityCode;
		this.noOfPackages = noOfPackages;
		this.cargoType = typeOfCargo;
		this.serviceName = serviceName;
		this.rates = rates;
		this.grossWeight = 	grossWeight;
		this.checkDate = "Y";
		this.taxPerc = taxPer;
	}
	
}

