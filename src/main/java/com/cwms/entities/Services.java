package com.cwms.entities;
import java.util.Date;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "services")
@IdClass(ServicesId.class)
public class Services {

    @Id
    @Column(name = "Service_Id", length = 6, nullable = true)
    private String serviceId;

    @Id
    @Column(name = "Company_Id", length = 6, nullable = true)
    private String companyId;
    
    @Id
    @Column(name = "Branch_Id", length = 6, nullable = true)
    private String branchId;

    @Column(name = "Service_Short_desc", length = 75, nullable = true)
    private String serviceShortDesc;

    @Column(name = "Service_Long_desc", length = 150, nullable = true)
    private String serviceLongDesc;

    @Column(name = "Service_Unit", length = 75, nullable = true)
    private String serviceUnit;

    @Column(name = "Service_Unit1", length = 75)
    private String serviceUnit1;

    @Column(name = "Service_Type", length = 75, nullable = true)
    private String serviceType;

    @Column(name = "Type_Of_Charges", length = 75, nullable = true)
    private String typeOfCharges;

    @Column(name = "Ac_Code", length = 10, nullable = true)
    private String acCode;

    @Column(name = "Tax_Applicable", length = 1, nullable = true)
    private String taxApplicable;

    @Column(name = "Tax_Id", length = 10, nullable = true)
    private String taxId;

    @Column(name = "Calculated_On_Service", length = 6, nullable = true)
    private String calculatedOnService;

    @Column(name = "Item_Type", length = 1, nullable = true)
    private String itemType;

    @Column(name = "Movement_Code_From", length = 1)
    private String movementCodeFrom;

    @Column(name = "Movement_Code_To", length = 1)
    private String movementCodeTo;

    @Column(name = "Expense_Item", length = 6, nullable = true)
    private String expenseItem;

    @Column(name = "Import_Export", length = 1, nullable = true)
    private String importExport;

    @Column(name = "Discount_Percentage", length = 1)
    private String discountPercentage;

    @Column(name = "Discount_Days", length = 1)
    private String discountDays;

    @Column(name = "Discount_Amt", length = 1)
    private String discountAmt;

    @Column(name = "Fixed_Rate", length = 1)
    private String fixedRate;

    @Column(name = "Commodity", length = 1)
    private String commodity;

    @Column(name = "Cargo_Wt_Vol", length = 1)
    private String cargoWtVol;

    @Column(name = "Container_Type", length = 10)
    private String containerType;

    @Column(name = "Container_Size", length = 6)
    private String containerSize;

    @Column(name = "Container_Status", length = 6, nullable = true)
    private String containerStatus;

    @Column(name = "Sector", length = 1)
    private String sector;

    @Column(name = "POL", length = 1)
    private String pol;

    @Column(name = "POD", length = 1)
    private String pod;

    @Column(name = "PLOD", length = 1)
    private String plod;

    @Column(name = "GRT", length = 1)
    private String grt;

    @Column(name = "NRT", length = 1)
    private String nrt;

    @Column(name = "Period", length = 1)
    private String period;

    @Column(name = "Range_Type", length = 4)
    private String rangeType;

    @Column(name = "Criteria_Type", length = 10, nullable = true)
    private String criteriaType;

    @Column(name = "Finance_Ledger_code", length = 20, nullable = true)
    private String financeLedgerCode;

    @Column(name = "Print_Sequence", nullable = true)
    private int printSequence;

    @Column(name = "Hazardous", length = 1)
    private String hazardous;

    @Column(name = "Place_From", length = 1)
    private String placeFrom;

    @Column(name = "Place_To", length = 1)
    private String placeTo;

    @Column(name = "Nature_Of_Cargo", length = 1)
    private String natureOfCargo;

    @Column(name = "Criteria_Status", length = 1)
    private String criteriaStatus;

    @Column(name = "Criteria_Edited_By", length = 10)
    private String criteriaEditedBy;

    @Column(name = "Criteria_Approved_By", length = 10)
    private String criteriaApprovedBy;

    @Column(name = "Sac_Code", length = 10)
    private String sacCode;

    @Column(name = "Service_New_desc", length = 50, nullable = true)
    private String serviceNewDesc;

    @Column(name = "Service_Change_Date", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date serviceChangeDate;

    @Column(name = "Service_Group", length = 1, nullable = true)
    private String serviceGroup;

    @Column(name = "CFS_Base_Rate", nullable = true)
    private BigDecimal cfsBaseRate;

    @Column(name = "Status", length = 1)
    private String status;

    @Column(name = "Created_By", length = 10, nullable = true)
    private String createdBy;

    @Column(name = "Created_Date", nullable = true)
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

    @Column(name = "Default_chk", length = 1, nullable = true)
    private String defaultChk;

    @Column(name = "hard_code", length = 1)
    private String hardCode;

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getServiceShortDesc() {
		return serviceShortDesc;
	}

	public void setServiceShortDesc(String serviceShortDesc) {
		this.serviceShortDesc = serviceShortDesc;
	}

	public String getServiceLongDesc() {
		return serviceLongDesc;
	}

	public void setServiceLongDesc(String serviceLongDesc) {
		this.serviceLongDesc = serviceLongDesc;
	}

	public String getServiceUnit() {
		return serviceUnit;
	}

	public void setServiceUnit(String serviceUnit) {
		this.serviceUnit = serviceUnit;
	}

	public String getServiceUnit1() {
		return serviceUnit1;
	}

	public void setServiceUnit1(String serviceUnit1) {
		this.serviceUnit1 = serviceUnit1;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getTypeOfCharges() {
		return typeOfCharges;
	}

	public void setTypeOfCharges(String typeOfCharges) {
		this.typeOfCharges = typeOfCharges;
	}

	public String getAcCode() {
		return acCode;
	}

	public void setAcCode(String acCode) {
		this.acCode = acCode;
	}

	public String getTaxApplicable() {
		return taxApplicable;
	}

	public void setTaxApplicable(String taxApplicable) {
		this.taxApplicable = taxApplicable;
	}

	public String getTaxId() {
		return taxId;
	}

	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}

	public String getCalculatedOnService() {
		return calculatedOnService;
	}

	public void setCalculatedOnService(String calculatedOnService) {
		this.calculatedOnService = calculatedOnService;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getMovementCodeFrom() {
		return movementCodeFrom;
	}

	public void setMovementCodeFrom(String movementCodeFrom) {
		this.movementCodeFrom = movementCodeFrom;
	}

	public String getMovementCodeTo() {
		return movementCodeTo;
	}

	public void setMovementCodeTo(String movementCodeTo) {
		this.movementCodeTo = movementCodeTo;
	}

	public String getExpenseItem() {
		return expenseItem;
	}

	public void setExpenseItem(String expenseItem) {
		this.expenseItem = expenseItem;
	}

	public String getImportExport() {
		return importExport;
	}

	public void setImportExport(String importExport) {
		this.importExport = importExport;
	}

	public String getDiscountPercentage() {
		return discountPercentage;
	}

	public void setDiscountPercentage(String discountPercentage) {
		this.discountPercentage = discountPercentage;
	}

	public String getDiscountDays() {
		return discountDays;
	}

	public void setDiscountDays(String discountDays) {
		this.discountDays = discountDays;
	}

	public String getDiscountAmt() {
		return discountAmt;
	}

	public void setDiscountAmt(String discountAmt) {
		this.discountAmt = discountAmt;
	}

	public String getFixedRate() {
		return fixedRate;
	}

	public void setFixedRate(String fixedRate) {
		this.fixedRate = fixedRate;
	}

	public String getCommodity() {
		return commodity;
	}

	public void setCommodity(String commodity) {
		this.commodity = commodity;
	}

	public String getCargoWtVol() {
		return cargoWtVol;
	}

	public void setCargoWtVol(String cargoWtVol) {
		this.cargoWtVol = cargoWtVol;
	}

	public String getContainerType() {
		return containerType;
	}

	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}

	public String getContainerSize() {
		return containerSize;
	}

	public void setContainerSize(String containerSize) {
		this.containerSize = containerSize;
	}

	public String getContainerStatus() {
		return containerStatus;
	}

	public void setContainerStatus(String containerStatus) {
		this.containerStatus = containerStatus;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public String getPol() {
		return pol;
	}

	public void setPol(String pol) {
		this.pol = pol;
	}

	public String getPod() {
		return pod;
	}

	public void setPod(String pod) {
		this.pod = pod;
	}

	public String getPlod() {
		return plod;
	}

	public void setPlod(String plod) {
		this.plod = plod;
	}

	public String getGrt() {
		return grt;
	}

	public void setGrt(String grt) {
		this.grt = grt;
	}

	public String getNrt() {
		return nrt;
	}

	public void setNrt(String nrt) {
		this.nrt = nrt;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getRangeType() {
		return rangeType;
	}

	public void setRangeType(String rangeType) {
		this.rangeType = rangeType;
	}

	public String getCriteriaType() {
		return criteriaType;
	}

	public void setCriteriaType(String criteriaType) {
		this.criteriaType = criteriaType;
	}

	public String getFinanceLedgerCode() {
		return financeLedgerCode;
	}

	public void setFinanceLedgerCode(String financeLedgerCode) {
		this.financeLedgerCode = financeLedgerCode;
	}

	public int getPrintSequence() {
		return printSequence;
	}

	public void setPrintSequence(int printSequence) {
		this.printSequence = printSequence;
	}

	public String getHazardous() {
		return hazardous;
	}

	public void setHazardous(String hazardous) {
		this.hazardous = hazardous;
	}

	public String getPlaceFrom() {
		return placeFrom;
	}

	public void setPlaceFrom(String placeFrom) {
		this.placeFrom = placeFrom;
	}

	public String getPlaceTo() {
		return placeTo;
	}

	public void setPlaceTo(String placeTo) {
		this.placeTo = placeTo;
	}

	public String getNatureOfCargo() {
		return natureOfCargo;
	}

	public void setNatureOfCargo(String natureOfCargo) {
		this.natureOfCargo = natureOfCargo;
	}

	public String getCriteriaStatus() {
		return criteriaStatus;
	}

	public void setCriteriaStatus(String criteriaStatus) {
		this.criteriaStatus = criteriaStatus;
	}

	public String getCriteriaEditedBy() {
		return criteriaEditedBy;
	}

	public void setCriteriaEditedBy(String criteriaEditedBy) {
		this.criteriaEditedBy = criteriaEditedBy;
	}

	public String getCriteriaApprovedBy() {
		return criteriaApprovedBy;
	}

	public void setCriteriaApprovedBy(String criteriaApprovedBy) {
		this.criteriaApprovedBy = criteriaApprovedBy;
	}

	public String getSacCode() {
		return sacCode;
	}

	public void setSacCode(String sacCode) {
		this.sacCode = sacCode;
	}

	public String getServiceNewDesc() {
		return serviceNewDesc;
	}

	public void setServiceNewDesc(String serviceNewDesc) {
		this.serviceNewDesc = serviceNewDesc;
	}

	public Date getServiceChangeDate() {
		return serviceChangeDate;
	}

	public void setServiceChangeDate(Date serviceChangeDate) {
		this.serviceChangeDate = serviceChangeDate;
	}

	public String getServiceGroup() {
		return serviceGroup;
	}

	public void setServiceGroup(String serviceGroup) {
		this.serviceGroup = serviceGroup;
	}

	public BigDecimal getCfsBaseRate() {
		return cfsBaseRate;
	}

	public void setCfsBaseRate(BigDecimal cfsBaseRate) {
		this.cfsBaseRate = cfsBaseRate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getDefaultChk() {
		return defaultChk;
	}

	public void setDefaultChk(String defaultChk) {
		this.defaultChk = defaultChk;
	}

	public String getHardCode() {
		return hardCode;
	}

	public void setHardCode(String hardCode) {
		this.hardCode = hardCode;
	}

	public Services() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public Services(String serviceId, String companyId, String branchId, String serviceShortDesc, String serviceLongDesc,
			String serviceUnit, String serviceUnit1, String serviceType, String typeOfCharges, String acCode,
			String taxApplicable, String taxId, String calculatedOnService, String itemType, String movementCodeFrom,
			String movementCodeTo, String expenseItem, String importExport, String discountPercentage,
			String discountDays, String discountAmt, String fixedRate, String commodity, String cargoWtVol,
			String containerType, String containerSize, String containerStatus, String sector, String pol, String pod,
			String plod, String grt, String nrt, String period, String rangeType, String criteriaType,
			String financeLedgerCode, int printSequence, String hazardous, String placeFrom, String placeTo,
			String natureOfCargo, String criteriaStatus, String criteriaEditedBy, String criteriaApprovedBy,
			String sacCode, String serviceNewDesc, Date serviceChangeDate, String serviceGroup,
			BigDecimal cfsBaseRate, String status, String createdBy, Date createdDate, String editedBy,
			Date editedDate, String approvedBy, Date approvedDate, String defaultChk,
			String hardCode) {
		super();
		this.serviceId = serviceId;
		this.companyId = companyId;
		this.branchId = branchId;
		this.serviceShortDesc = serviceShortDesc;
		this.serviceLongDesc = serviceLongDesc;
		this.serviceUnit = serviceUnit;
		this.serviceUnit1 = serviceUnit1;
		this.serviceType = serviceType;
		this.typeOfCharges = typeOfCharges;
		this.acCode = acCode;
		this.taxApplicable = taxApplicable;
		this.taxId = taxId;
		this.calculatedOnService = calculatedOnService;
		this.itemType = itemType;
		this.movementCodeFrom = movementCodeFrom;
		this.movementCodeTo = movementCodeTo;
		this.expenseItem = expenseItem;
		this.importExport = importExport;
		this.discountPercentage = discountPercentage;
		this.discountDays = discountDays;
		this.discountAmt = discountAmt;
		this.fixedRate = fixedRate;
		this.commodity = commodity;
		this.cargoWtVol = cargoWtVol;
		this.containerType = containerType;
		this.containerSize = containerSize;
		this.containerStatus = containerStatus;
		this.sector = sector;
		this.pol = pol;
		this.pod = pod;
		this.plod = plod;
		this.grt = grt;
		this.nrt = nrt;
		this.period = period;
		this.rangeType = rangeType;
		this.criteriaType = criteriaType;
		this.financeLedgerCode = financeLedgerCode;
		this.printSequence = printSequence;
		this.hazardous = hazardous;
		this.placeFrom = placeFrom;
		this.placeTo = placeTo;
		this.natureOfCargo = natureOfCargo;
		this.criteriaStatus = criteriaStatus;
		this.criteriaEditedBy = criteriaEditedBy;
		this.criteriaApprovedBy = criteriaApprovedBy;
		this.sacCode = sacCode;
		this.serviceNewDesc = serviceNewDesc;
		this.serviceChangeDate = serviceChangeDate;
		this.serviceGroup = serviceGroup;
		this.cfsBaseRate = cfsBaseRate;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.editedBy = editedBy;
		this.editedDate = editedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.defaultChk = defaultChk;
		this.hardCode = hardCode;
	}

	@Override
	public String toString() {
		return "Service [serviceId=" + serviceId + ", companyId=" + companyId + ", serviceShortDesc=" + serviceShortDesc
				+ ", serviceLongDesc=" + serviceLongDesc + ", serviceUnit=" + serviceUnit + ", serviceUnit1="
				+ serviceUnit1 + ", serviceType=" + serviceType + ", typeOfCharges=" + typeOfCharges + ", acCode="
				+ acCode + ", taxApplicable=" + taxApplicable + ", taxId=" + taxId + ", calculatedOnService="
				+ calculatedOnService + ", itemType=" + itemType + ", movementCodeFrom=" + movementCodeFrom
				+ ", movementCodeTo=" + movementCodeTo + ", expenseItem=" + expenseItem + ", importExport="
				+ importExport + ", discountPercentage=" + discountPercentage + ", discountDays=" + discountDays
				+ ", discountAmt=" + discountAmt + ", fixedRate=" + fixedRate + ", commodity=" + commodity
				+ ", cargoWtVol=" + cargoWtVol + ", containerType=" + containerType + ", containerSize=" + containerSize
				+ ", containerStatus=" + containerStatus + ", sector=" + sector + ", pol=" + pol + ", pod=" + pod
				+ ", plod=" + plod + ", grt=" + grt + ", nrt=" + nrt + ", period=" + period + ", rangeType=" + rangeType
				+ ", criteriaType=" + criteriaType + ", financeLedgerCode=" + financeLedgerCode + ", printSequence="
				+ printSequence + ", hazardous=" + hazardous + ", placeFrom=" + placeFrom + ", placeTo=" + placeTo
				+ ", natureOfCargo=" + natureOfCargo + ", criteriaStatus=" + criteriaStatus + ", criteriaEditedBy="
				+ criteriaEditedBy + ", criteriaApprovedBy=" + criteriaApprovedBy + ", sacCode=" + sacCode
				+ ", serviceNewDesc=" + serviceNewDesc + ", serviceChangeDate=" + serviceChangeDate + ", serviceGroup="
				+ serviceGroup + ", cfsBaseRate=" + cfsBaseRate + ", status=" + status + ", createdBy=" + createdBy
				+ ", createdDate=" + createdDate + ", editedBy=" + editedBy + ", editedDate=" + editedDate
				+ ", approvedBy=" + approvedBy + ", approvedDate=" + approvedDate + ", defaultChk=" + defaultChk
				+ ", hardCode=" + hardCode + "]";
	}

	public Services(String serviceId, String serviceShortDesc, String serviceType, String sacCode, String status,
			String createdBy) {
		super();
		this.serviceId = serviceId;
		this.serviceShortDesc = serviceShortDesc;
		this.serviceType = serviceType;
		this.sacCode = sacCode;
		this.status = status;
		this.createdBy = createdBy;
	}
	
	
	
	
}
