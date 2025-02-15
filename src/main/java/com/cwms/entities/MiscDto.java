package com.cwms.entities;

import java.math.BigDecimal;

import jakarta.persistence.Column;

public class MiscDto {

	public String assesmentId;
	public String serviceId;
	public String serviceDesc;
	public String sacCode;
	public String taxPerc;
	public String serviceUnit;
	public String executionUnit;
	public String serviceUnit1;
	public String executionUnit1;
	public BigDecimal rate;
	public BigDecimal totalRate;
	public String tariffNo;
	public String amdNo;
	public String taxId;
	protected MiscDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	protected MiscDto(String assesmentId, String serviceId, String serviceDesc, String sacCode, String taxPerc,
			String serviceUnit, String executionUnit, String serviceUnit1, String executionUnit1, BigDecimal rate,
			BigDecimal totalRate, String tariffNo, String amdNo, String taxId) {
		super();
		this.assesmentId = assesmentId;
		this.serviceId = serviceId;
		this.serviceDesc = serviceDesc;
		this.sacCode = sacCode;
		this.taxPerc = taxPerc;
		this.serviceUnit = serviceUnit;
		this.executionUnit = executionUnit;
		this.serviceUnit1 = serviceUnit1;
		this.executionUnit1 = executionUnit1;
		this.rate = rate;
		this.totalRate = totalRate;
		this.tariffNo = tariffNo;
		this.amdNo = amdNo;
		this.taxId = taxId;
	}

	public String getAssesmentId() {
		return assesmentId;
	}
	public void setAssesmentId(String assesmentId) {
		this.assesmentId = assesmentId;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getServiceDesc() {
		return serviceDesc;
	}
	public void setServiceDesc(String serviceDesc) {
		this.serviceDesc = serviceDesc;
	}
	public String getSacCode() {
		return sacCode;
	}
	public void setSacCode(String sacCode) {
		this.sacCode = sacCode;
	}
	public String getTaxPerc() {
		return taxPerc;
	}
	public void setTaxPerc(String taxPerc) {
		this.taxPerc = taxPerc;
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
	public BigDecimal getRate() {
		return rate;
	}
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
	public BigDecimal getTotalRate() {
		return totalRate;
	}
	public void setTotalRate(BigDecimal totalRate) {
		this.totalRate = totalRate;
	}
	public String getTariffNo() {
		return tariffNo;
	}
	public void setTariffNo(String tariffNo) {
		this.tariffNo = tariffNo;
	}
	public String getAmdNo() {
		return amdNo;
	}
	public void setAmdNo(String amdNo) {
		this.amdNo = amdNo;
	}
	
	public String getTaxId() {
		return taxId;
	}

	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}

	@Override
	public String toString() {
		return "MiscDto [assesmentId=" + assesmentId + ", serviceId=" + serviceId + ", serviceDesc=" + serviceDesc
				+ ", sacCode=" + sacCode + ", taxPerc=" + taxPerc + ", serviceUnit=" + serviceUnit + ", executionUnit="
				+ executionUnit + ", serviceUnit1=" + serviceUnit1 + ", executionUnit1=" + executionUnit1 + ", rate="
				+ rate + ", totalRate=" + totalRate + ", tariffNo=" + tariffNo + ", amdNo=" + amdNo + "]";
	}
	
	
	
}
