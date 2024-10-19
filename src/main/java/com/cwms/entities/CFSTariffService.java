package com.cwms.entities;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "cfstdtrfsrv")
@IdClass(CfstdtrfsrvId.class)
public class CFSTariffService 
{
	
	    @Id
	    @Column(name = "Company_Id", length = 10)
	    private String companyId;

	    @Id
	    @Column(name = "Branch_Id", length = 10)
	    private String branchId;

	    @Id
	    @Column(name = "Fin_Year", length = 6)
	    private String finYear;

	    @Id
	    @Column(name = "CFS_Tariff_No", length = 10)
	    private String cfsTariffNo;

	    @Id
	    @Column(name = "CFS_Amend_No", length = 10)
	    private String cfsAmendNo;

	    @Id
	    @Column(name = "Service_Id", length = 10)
	    private String serviceId;

	    @Id
	    @Column(name = "Sr_No", precision = 8, scale = 0)
	    private BigDecimal srNo;

	    @Id
	    @Column(name = "Container_Size", length = 5)
	    private String containerSize;

	    @Id
	    @Column(name = "Commodity_Code", length = 7)
	    private String commodityCode;

	    @Id
	    @Column(name = "Cargo_Type", length = 10)
	    private String cargoType;

	    @Id
	    @Column(name = "Status", length = 1)
	    private String status;

	    @Column(name = "Range_Type", length = 10)
	    private String rangeType;

	    @Column(name = "ProfitCentre_Id", length = 10)
	    private String profitCentreId;

	    @Column(name = "Service_Unit", length = 10)
	    private String serviceUnit;

	    @Column(name = "Service_UnitI", length = 10)
	    private String serviceUnitI;

	    @Column(name = "From_Range", precision = 16, scale = 3)
	    private BigDecimal fromRange;

	    @Column(name = "To_Range", precision = 16, scale = 3)
	    private BigDecimal toRange;

	    @Column(name = "Rate", precision = 16, scale = 3)
	    private BigDecimal rate;

	    @Column(name = "Minimum_Rate", precision = 16, scale = 3)
	    private BigDecimal minimumRate;

	    @Column(name = "Created_By", length = 10)
	    private String createdBy;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "Created_Date")
	    private Date createdDate;

	    @Column(name = "Edited_By", length = 10)
	    private String editedBy;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "Edited_Date")
	    private Date editedDate;

	    @Column(name = "Approved_By", length = 10)
	    private String approvedBy;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name = "Approved_Date")
	    private Date approvedDate;

	    @Column(name = "Currency_Id", length = 10)
	    private String currencyId;

	    @Column(name = "AmmendStatus", length = 1)
	    private String ammendStatus;

	    @Column(name = "Default_chk", length = 1, columnDefinition = "char(1) default 'N'")
	    private String defaultChk = "N";

		
		public CFSTariffService(String companyId, String branchId, String finYear, String cfsTariffNo,
				String cfsAmendNo, String serviceId, BigDecimal srNo, String containerSize, String commodityCode,
				String cargoType, String status, String rangeType, String profitCentreId, String serviceUnit,
				String serviceUnitI, BigDecimal fromRange, BigDecimal toRange, BigDecimal rate, BigDecimal minimumRate,
				String createdBy, Date createdDate, String editedBy, Date editedDate, String approvedBy,
				Date approvedDate, String currencyId, String ammendStatus, String defaultChk) {
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
			this.rangeType = rangeType;
			this.profitCentreId = profitCentreId;
			this.serviceUnit = serviceUnit;
			this.serviceUnitI = serviceUnitI;
			this.fromRange = fromRange;
			this.toRange = toRange;
			this.rate = rate;
			this.minimumRate = minimumRate;
			this.createdBy = createdBy;
			this.createdDate = createdDate;
			this.editedBy = editedBy;
			this.editedDate = editedDate;
			this.approvedBy = approvedBy;
			this.approvedDate = approvedDate;
			this.currencyId = currencyId;
			this.ammendStatus = ammendStatus;
			this.defaultChk = defaultChk;
		}

		public CFSTariffService() {
			super();
			// TODO Auto-generated constructor stub
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

		public String getRangeType() {
			return rangeType;
		}

		public void setRangeType(String rangeType) {
			this.rangeType = rangeType;
		}

		public String getProfitCentreId() {
			return profitCentreId;
		}

		public void setProfitCentreId(String profitCentreId) {
			this.profitCentreId = profitCentreId;
		}

		public String getServiceUnit() {
			return serviceUnit;
		}

		public void setServiceUnit(String serviceUnit) {
			this.serviceUnit = serviceUnit;
		}

		public String getServiceUnitI() {
			return serviceUnitI;
		}

		public void setServiceUnitI(String serviceUnitI) {
			this.serviceUnitI = serviceUnitI;
		}

		public BigDecimal getFromRange() {
			return fromRange;
		}

		public void setFromRange(BigDecimal fromRange) {
			this.fromRange = fromRange;
		}

		public BigDecimal getToRange() {
			return toRange;
		}

		public void setToRange(BigDecimal toRange) {
			this.toRange = toRange;
		}

		public BigDecimal getRate() {
			return rate;
		}

		public void setRate(BigDecimal rate) {
			this.rate = rate;
		}

		public BigDecimal getMinimumRate() {
			return minimumRate;
		}

		public void setMinimumRate(BigDecimal minimumRate) {
			this.minimumRate = minimumRate;
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

		public String getCurrencyId() {
			return currencyId;
		}

		public void setCurrencyId(String currencyId) {
			this.currencyId = currencyId;
		}

		public String getAmmendStatus() {
			return ammendStatus;
		}

		public void setAmmendStatus(String ammendStatus) {
			this.ammendStatus = ammendStatus;
		}

		public String getDefaultChk() {
			return defaultChk;
		}

		public void setDefaultChk(String defaultChk) {
			this.defaultChk = defaultChk;
		}

		public CFSTariffService(String serviceId, BigDecimal rate) {
			super();
			this.serviceId = serviceId;
			this.rate = rate;
		}
	    
	    
	    
	
	
}