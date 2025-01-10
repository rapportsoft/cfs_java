package com.cwms.entities;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;

public class AssessmentContainerDTO implements Cloneable {

	public String containerNo;
	public String containerSize;
	public String containerType;
	public Date gateInDate;
	public Date destuffDate;
	public Date gateoutDate;
	public String examPercentage;
	public String typeOfContainer;
	public String scannerType;
	public String gateOutType;
	public String checkDate;
	public Date invoiceDate;
	public String upTariffNo;
	public String profitcentreId;
	public String serviceId;
	public String serviceName;
	public BigDecimal rates;
	public BigDecimal grossWt;
	public BigDecimal cargoWt;
	public String ssrTransId;
	public Date invoiceUptoDate;
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
	
	public AssessmentContainerDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	




	 public Date getInvoiceUptoDate() {
		return invoiceUptoDate;
	}







	public void setInvoiceUptoDate(Date invoiceUptoDate) {
		this.invoiceUptoDate = invoiceUptoDate;
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







	public Date getGateInDate() {
		return gateInDate;
	}







	public void setGateInDate(Date gateInDate) {
		this.gateInDate = gateInDate;
	}







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







	public String getTypeOfContainer() {
		return typeOfContainer;
	}







	public void setTypeOfContainer(String typeOfContainer) {
		this.typeOfContainer = typeOfContainer;
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







	public Date getInvoiceDate() {
		return invoiceDate;
	}







	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}







	public String getUpTariffNo() {
		return upTariffNo;
	}







	public void setUpTariffNo(String upTariffNo) {
		this.upTariffNo = upTariffNo;
	}







	public String getProfitcentreId() {
		return profitcentreId;
	}







	public void setProfitcentreId(String profitcentreId) {
		this.profitcentreId = profitcentreId;
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







	public String getSsrTransId() {
		return ssrTransId;
	}







	public void setSsrTransId(String ssrTransId) {
		this.ssrTransId = ssrTransId;
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












	public AssessmentContainerDTO(String containerNo, String containerSize, String containerType, Date gateInDate,
			Date destuffDate, Date gateoutDate, String examPercentage, String typeOfContainer, String scannerType,
			String gateOutType, String checkDate, Date invoiceDate, String upTariffNo, String profitcentreId,
			String serviceId, String serviceName, BigDecimal rates, BigDecimal grossWt, BigDecimal cargoWt,
			String ssrTransId, Date invoiceUptoDate, Date lastInvoiceUptoDate, String serviceUnit, String executionUnit,
			String serviceUnit1, String executionUnit1, String currencyId, BigDecimal discPercentage,
			BigDecimal discValue, BigDecimal mPercentage, BigDecimal mAmount, String woNo, String woAmndNo,
			String criteria, BigDecimal rangeFrom, BigDecimal rangeTo, String containerStatus, String gateOutId,
			String gatePassNo, String taxApp, String acCode, BigDecimal serviceRate, BigDecimal taxPerc, String taxId,
			BigDecimal exRate, String serviceGroup) {
		this.containerNo = containerNo;
		this.containerSize = containerSize;
		this.containerType = containerType;
		this.gateInDate = gateInDate;
		this.destuffDate = destuffDate;
		this.gateoutDate = gateoutDate;
		this.examPercentage = examPercentage;
		this.typeOfContainer = typeOfContainer;
		this.scannerType = scannerType;
		this.gateOutType = gateOutType;
		this.checkDate = checkDate;
		this.invoiceDate = invoiceDate;
		this.upTariffNo = upTariffNo;
		this.profitcentreId = profitcentreId;
		this.serviceId = serviceId;
		this.serviceName = serviceName;
		this.rates = rates;
		this.grossWt = grossWt;
		this.cargoWt = cargoWt;
		this.ssrTransId = ssrTransId;
		this.invoiceUptoDate = invoiceUptoDate;
		this.lastInvoiceUptoDate = lastInvoiceUptoDate;
		this.serviceUnit = serviceUnit;
		this.executionUnit = executionUnit;
		this.serviceUnit1 = serviceUnit1;
		this.executionUnit1 = executionUnit1;
		this.currencyId = currencyId;
		this.discPercentage = discPercentage;
		this.discValue = discValue;
		this.mPercentage = mPercentage;
		this.mAmount = mAmount;
		this.woNo = woNo;
		this.woAmndNo = woAmndNo;
		this.criteria = criteria;
		this.rangeFrom = rangeFrom;
		this.rangeTo = rangeTo;
		this.containerStatus = containerStatus;
		this.gateOutId = gateOutId;
		this.gatePassNo = gatePassNo;
		this.taxApp = taxApp;
		this.acCode = acCode;
		this.serviceRate = serviceRate;
		this.taxPerc = taxPerc;
		this.taxId = taxId;
		this.exRate = exRate;
		this.serviceGroup = serviceGroup;
	}







	// Override clone() method
		@Override
		public Object clone() throws CloneNotSupportedException {
		    return super.clone();
		}







   


	
	
	
	
}
