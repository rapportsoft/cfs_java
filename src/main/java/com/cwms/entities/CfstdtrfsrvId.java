package com.cwms.entities;

import java.io.Serializable;
import java.math.BigDecimal;

public class CfstdtrfsrvId implements Serializable {

	    private String companyId;

	    private String branchId;

	    private String finYear;

	    private String cfsTariffNo;

	    private String cfsAmendNo;

	    private String serviceId;

	    private BigDecimal srNo;

	    private String containerSize;

	    private String commodityCode;

	    private String cargoType;

	    private String status;

		public CfstdtrfsrvId() {
			super();
			// TODO Auto-generated constructor stub
		}

		public CfstdtrfsrvId(String companyId, String branchId, String finYear, String cfsTariffNo, String cfsAmendNo,
				String serviceId, BigDecimal srNo, String containerSize, String commodityCode, String cargoType,
				String status) {
			super();
			this.companyId = companyId;
			this.branchId = branchId;
			this.finYear = finYear;
			this.cfsTariffNo = cfsTariffNo;
			this.cfsAmendNo = cfsAmendNo;
			this.serviceId = serviceId;
			this.srNo = srNo;
			this.containerSize = containerSize;
			this.commodityCode = commodityCode;
			this.cargoType = cargoType;
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

		public String getFinYear() {
			return finYear;
		}

		public void setFinYear(String finYear) {
			this.finYear = finYear;
		}

		public String getCfsTariffNo() {
			return cfsTariffNo;
		}

		public void setCfsTariffNo(String cfsTariffNo) {
			this.cfsTariffNo = cfsTariffNo;
		}

		public String getCfsAmendNo() {
			return cfsAmendNo;
		}

		public void setCfsAmendNo(String cfsAmendNo) {
			this.cfsAmendNo = cfsAmendNo;
		}

		public String getServiceId() {
			return serviceId;
		}

		public void setServiceId(String serviceId) {
			this.serviceId = serviceId;
		}

		public BigDecimal getSrNo() {
			return srNo;
		}

		public void setSrNo(BigDecimal srNo) {
			this.srNo = srNo;
		}

		public String getContainerSize() {
			return containerSize;
		}

		public void setContainerSize(String containerSize) {
			this.containerSize = containerSize;
		}

		public String getCommodityCode() {
			return commodityCode;
		}

		public void setCommodityCode(String commodityCode) {
			this.commodityCode = commodityCode;
		}

		public String getCargoType() {
			return cargoType;
		}

		public void setCargoType(String cargoType) {
			this.cargoType = cargoType;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}
	    
	    
}
