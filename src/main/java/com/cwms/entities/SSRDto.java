package com.cwms.entities;

import java.math.BigDecimal;

public class SSRDto {
     public String serviceId;
     public String serviceDesc;
     public String serviceUnit;
     public String executionUnit;
     public BigDecimal rate;
     public BigDecimal totalRate;
	public SSRDto(String serviceId, String serviceDesc, String serviceUnit, String executionUnit, BigDecimal rate,
			BigDecimal totalRate) {
		super();
		this.serviceId = serviceId;
		this.serviceDesc = serviceDesc;
		this.serviceUnit = serviceUnit;
		this.executionUnit = executionUnit;
		this.rate = rate;
		this.totalRate = totalRate;
	}
	public SSRDto() {
		super();
		// TODO Auto-generated constructor stub
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
	public String getServiceUnit() {
		return serviceUnit;
	}
	public void setServiceUnit(String serviceUnit) {
		this.serviceUnit = serviceUnit;
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
	public String getExecutionUnit() {
		return executionUnit;
	}
	public void setExecutionUnit(String executionUnit) {
		this.executionUnit = executionUnit;
	}
     
     
     
}
