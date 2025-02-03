package com.cwms.entities;

import java.math.BigDecimal;

public class ExportSSRDto {

	public String companyId;
	public String branchId;
	public String containerNo;
	public String containerSize;
	public String containerType;
	public String gateOutType;
	public BigDecimal cargoWt;
	public int noOfPackages;
	public String stuffTallyId;
	public String cha;
	public String sa;
	public String sl;
	public String onAccountOf;
	public String impId;	
	
	public ExportSSRDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ExportSSRDto(String companyId, String branchId, String containerNo, String containerSize,
			String containerType, String gateOutType, BigDecimal cargoWt, int noOfPackages, String stuffTallyId,
			String cha, String sa, String sl, String onAccountOf, String impId) {
		this.companyId = companyId;
		this.branchId = branchId;
		this.containerNo = containerNo;
		this.containerSize = containerSize;
		this.containerType = containerType;
		this.gateOutType = gateOutType;
		this.cargoWt = cargoWt;
		this.noOfPackages = noOfPackages;
		this.stuffTallyId = stuffTallyId;
		this.cha = cha;
		this.sa = sa;
		this.sl = sl;
		this.onAccountOf = onAccountOf;
		this.impId = impId;
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
	public String getGateOutType() {
		return gateOutType;
	}
	public void setGateOutType(String gateOutType) {
		this.gateOutType = gateOutType;
	}
	public BigDecimal getCargoWt() {
		return cargoWt;
	}
	public void setCargoWt(BigDecimal cargoWt) {
		this.cargoWt = cargoWt;
	}
	public int getNoOfPackages() {
		return noOfPackages;
	}
	public void setNoOfPackages(int noOfPackages) {
		this.noOfPackages = noOfPackages;
	}
	public String getStuffTallyId() {
		return stuffTallyId;
	}
	public void setStuffTallyId(String stuffTallyId) {
		this.stuffTallyId = stuffTallyId;
	}

	public String getCha() {
		return cha;
	}

	public void setCha(String cha) {
		this.cha = cha;
	}

	public String getSa() {
		return sa;
	}

	public void setSa(String sa) {
		this.sa = sa;
	}

	public String getSl() {
		return sl;
	}

	public void setSl(String sl) {
		this.sl = sl;
	}

	public String getOnAccountOf() {
		return onAccountOf;
	}

	public void setOnAccountOf(String onAccountOf) {
		this.onAccountOf = onAccountOf;
	}

	public String getImpId() {
		return impId;
	}

	public void setImpId(String impId) {
		this.impId = impId;
	}
	
	
}
