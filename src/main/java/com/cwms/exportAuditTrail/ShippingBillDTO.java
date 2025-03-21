package com.cwms.exportAuditTrail;

import java.math.BigDecimal;
import java.util.Date;

public class ShippingBillDTO {
	
	    private String sbtransid; // Transaction ID
	    private String auditremarks; // Audit remarks
	    private String profitCentreId; // Profit Centre ID
	    private String companyId; // Company ID
	    private String branchId; // Branch ID
	    private String finYear; // Financial Year
	    private String userId; // User ID

	    // Fields with old and new values
	    private String oldSbNo; // Old SB No
	    private String newSbNo; // New SB No

	    private Date oldSbDate; // Old SB Date
	    private Date newSbDate; // New SB Date

	    private BigDecimal oldNoOfPackages; // Old No of Packages
	    private BigDecimal newNoOfPackages; // New No of Packages

	    private BigDecimal oldGrossWeight; // Old Gross Weight
	    private BigDecimal newGrossWeight; // New Gross Weight

	    private String oldPackagesType; // Old Packages Type
	    private String newPackagesType; // New Packages Type

	    private String oldExporter; // Old Exporter
	    private String newExporter; // New Exporter

	    private String oldCargoType; // Old Cargo Type
	    private String newCargoType; // New Cargo Type

	    private BigDecimal oldFOB; // Old FOB
	    private BigDecimal newFOB; // New FOB

	    private String oldCommodityId; // Old Commodity ID
	    private String newCommodityId; // New Commodity ID

	    private String oldNoOfMarks; // Old No of Marks
	    private String newNoOfMarks; // New No of Marks

	    private String oldConsigneeName; // Old Consignee Name
	    private String newConsigneeName; // New Consignee Name

	    private String oldCHA; // Old CHA
	    private String newCHA; // New CHA
	    private String chaName;

	    private String oldOnAcc; // Old On Account Of
	    private String newOnAcc; // New On Account Of
	    private String onAccountName; // Old On Account Of Name
	    
	    private String oldSbType; // Old Consignee Name
	    private String newSbType; // New Consignee Name

	    private String oldExporterId;
	    private String newExporterId;
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    private String oldExporterAddress1; // Old Packages Type
	    private String newExporterAddress1; // New Packages Type

	    private String oldExporterAddress2; // Old Exporter
	    private String newExporterAddress2; // New Exporter

	    private String oldExporterAddress3; // Old Cargo Type
	    private String newExporterAddress3; // New Cargo Type

	    private String oldSrNo; // Old FOB
	    private String newSrNo; // New FOB

	    private String oldGstNo; // Old Commodity ID
	    private String newGstNo; // New Commodity ID

	    private String oldState; // Old No of Marks
	    private String newState; // New No of Marks

	    private String oldIecCode; // Old Consignee Name
	    private String newIecCode; // New Consignee Name

	    
		public ShippingBillDTO() {
			super();
			// TODO Auto-generated constructor stub
		}
				
		public ShippingBillDTO(String sbtransid, String auditremarks, String profitCentreId, String companyId,
				String branchId, String finYear, String userId, String oldSbNo, String newSbNo, Date oldSbDate,
				Date newSbDate, BigDecimal oldNoOfPackages, BigDecimal newNoOfPackages, BigDecimal oldGrossWeight,
				BigDecimal newGrossWeight, String oldPackagesType, String newPackagesType, String oldExporter,
				String newExporter, String oldCargoType, String newCargoType, BigDecimal oldFOB, BigDecimal newFOB,
				String oldCommodityId, String newCommodityId, String oldNoOfMarks, String newNoOfMarks,
				String oldConsigneeName, String newConsigneeName, String oldCHA, String newCHA, String chaName,
				String oldOnAcc, String newOnAcc, String onAccountName) {
			super();
			this.sbtransid = sbtransid;
			this.auditremarks = auditremarks;
			this.profitCentreId = profitCentreId;
			this.companyId = companyId;
			this.branchId = branchId;
			this.finYear = finYear;
			this.userId = userId;
			this.oldSbNo = oldSbNo;
			this.newSbNo = newSbNo;
			this.oldSbDate = oldSbDate;
			this.newSbDate = newSbDate;
			this.oldNoOfPackages = oldNoOfPackages;
			this.newNoOfPackages = newNoOfPackages;
			this.oldGrossWeight = oldGrossWeight;
			this.newGrossWeight = newGrossWeight;
			this.oldPackagesType = oldPackagesType;
			this.newPackagesType = newPackagesType;
			this.oldExporter = oldExporter;
			this.newExporter = newExporter;
			this.oldCargoType = oldCargoType;
			this.newCargoType = newCargoType;
			this.oldFOB = oldFOB;
			this.newFOB = newFOB;
			this.oldCommodityId = oldCommodityId;
			this.newCommodityId = newCommodityId;
			this.oldNoOfMarks = oldNoOfMarks;
			this.newNoOfMarks = newNoOfMarks;
			this.oldConsigneeName = oldConsigneeName;
			this.newConsigneeName = newConsigneeName;
			this.oldCHA = oldCHA;
			this.newCHA = newCHA;
			this.chaName = chaName;
			this.oldOnAcc = oldOnAcc;
			this.newOnAcc = newOnAcc;
			this.onAccountName = onAccountName;
		}

		public String getChaName() {
			return chaName;
		}

		public void setChaName(String chaName) {
			this.chaName = chaName;
		}

		public String getOnAccountName() {
			return onAccountName;
		}

		public void setOnAccountName(String onAccountName) {
			this.onAccountName = onAccountName;
		}

		public String getSbtransid() {
			return sbtransid;
		}
		public void setSbtransid(String sbtransid) {
			this.sbtransid = sbtransid;
		}
		public String getAuditremarks() {
			return auditremarks;
		}
		public void setAuditremarks(String auditremarks) {
			this.auditremarks = auditremarks;
		}
		public String getProfitCentreId() {
			return profitCentreId;
		}
		public void setProfitCentreId(String profitCentreId) {
			this.profitCentreId = profitCentreId;
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
		public String getUserId() {
			return userId;
		}
		public void setUserId(String userId) {
			this.userId = userId;
		}
		public String getOldSbNo() {
			return oldSbNo;
		}
		public void setOldSbNo(String oldSbNo) {
			this.oldSbNo = oldSbNo;
		}
		public String getNewSbNo() {
			return newSbNo;
		}
		public void setNewSbNo(String newSbNo) {
			this.newSbNo = newSbNo;
		}
		public Date getOldSbDate() {
			return oldSbDate;
		}
		public void setOldSbDate(Date oldSbDate) {
			this.oldSbDate = oldSbDate;
		}
		public Date getNewSbDate() {
			return newSbDate;
		}
		public void setNewSbDate(Date newSbDate) {
			this.newSbDate = newSbDate;
		}
		public BigDecimal getOldNoOfPackages() {
			return oldNoOfPackages;
		}
		public void setOldNoOfPackages(BigDecimal oldNoOfPackages) {
			this.oldNoOfPackages = oldNoOfPackages;
		}
		public BigDecimal getNewNoOfPackages() {
			return newNoOfPackages;
		}
		public void setNewNoOfPackages(BigDecimal newNoOfPackages) {
			this.newNoOfPackages = newNoOfPackages;
		}
		public BigDecimal getOldGrossWeight() {
			return oldGrossWeight;
		}
		public void setOldGrossWeight(BigDecimal oldGrossWeight) {
			this.oldGrossWeight = oldGrossWeight;
		}
		public BigDecimal getNewGrossWeight() {
			return newGrossWeight;
		}
		public void setNewGrossWeight(BigDecimal newGrossWeight) {
			this.newGrossWeight = newGrossWeight;
		}
		public String getOldPackagesType() {
			return oldPackagesType;
		}
		public void setOldPackagesType(String oldPackagesType) {
			this.oldPackagesType = oldPackagesType;
		}
		public String getNewPackagesType() {
			return newPackagesType;
		}
		public void setNewPackagesType(String newPackagesType) {
			this.newPackagesType = newPackagesType;
		}
		public String getOldExporter() {
			return oldExporter;
		}
		public void setOldExporter(String oldExporter) {
			this.oldExporter = oldExporter;
		}
		public String getNewExporter() {
			return newExporter;
		}
		public void setNewExporter(String newExporter) {
			this.newExporter = newExporter;
		}
		public String getOldCargoType() {
			return oldCargoType;
		}
		public void setOldCargoType(String oldCargoType) {
			this.oldCargoType = oldCargoType;
		}
		public String getNewCargoType() {
			return newCargoType;
		}
		public void setNewCargoType(String newCargoType) {
			this.newCargoType = newCargoType;
		}
		public BigDecimal getOldFOB() {
			return oldFOB;
		}
		public void setOldFOB(BigDecimal oldFOB) {
			this.oldFOB = oldFOB;
		}
		public BigDecimal getNewFOB() {
			return newFOB;
		}
		public void setNewFOB(BigDecimal newFOB) {
			this.newFOB = newFOB;
		}
		public String getOldCommodityId() {
			return oldCommodityId;
		}
		public void setOldCommodityId(String oldCommodityId) {
			this.oldCommodityId = oldCommodityId;
		}
		public String getNewCommodityId() {
			return newCommodityId;
		}
		public void setNewCommodityId(String newCommodityId) {
			this.newCommodityId = newCommodityId;
		}
		public String getOldNoOfMarks() {
			return oldNoOfMarks;
		}
		public void setOldNoOfMarks(String oldNoOfMarks) {
			this.oldNoOfMarks = oldNoOfMarks;
		}
		public String getNewNoOfMarks() {
			return newNoOfMarks;
		}
		public void setNewNoOfMarks(String newNoOfMarks) {
			this.newNoOfMarks = newNoOfMarks;
		}
		public String getOldConsigneeName() {
			return oldConsigneeName;
		}
		public void setOldConsigneeName(String oldConsigneeName) {
			this.oldConsigneeName = oldConsigneeName;
		}
		public String getNewConsigneeName() {
			return newConsigneeName;
		}
		public void setNewConsigneeName(String newConsigneeName) {
			this.newConsigneeName = newConsigneeName;
		}
		public String getOldCHA() {
			return oldCHA;
		}
		public void setOldCHA(String oldCHA) {
			this.oldCHA = oldCHA;
		}
		public String getNewCHA() {
			return newCHA;
		}
		public void setNewCHA(String newCHA) {
			this.newCHA = newCHA;
		}
		public String getOldOnAcc() {
			return oldOnAcc;
		}
		public void setOldOnAcc(String oldOnAcc) {
			this.oldOnAcc = oldOnAcc;
		}
		public String getNewOnAcc() {
			return newOnAcc;
		}
		public void setNewOnAcc(String newOnAcc) {
			this.newOnAcc = newOnAcc;
		}
		
		public String getOldSbType() {
			return oldSbType;
		}

		public void setOldSbType(String oldSbType) {
			this.oldSbType = oldSbType;
		}

		public String getNewSbType() {
			return newSbType;
		}

		public void setNewSbType(String newSbType) {
			this.newSbType = newSbType;
		}

		public String getOldExporterId() {
			return oldExporterId;
		}

		public void setOldExporterId(String oldExporterId) {
			this.oldExporterId = oldExporterId;
		}

		public String getNewExporterId() {
			return newExporterId;
		}

		public void setNewExporterId(String newExporterId) {
			this.newExporterId = newExporterId;
		}

		public String getOldExporterAddress1() {
			return oldExporterAddress1;
		}

		public void setOldExporterAddress1(String oldExporterAddress1) {
			this.oldExporterAddress1 = oldExporterAddress1;
		}

		public String getNewExporterAddress1() {
			return newExporterAddress1;
		}

		public void setNewExporterAddress1(String newExporterAddress1) {
			this.newExporterAddress1 = newExporterAddress1;
		}

		public String getOldExporterAddress2() {
			return oldExporterAddress2;
		}

		public void setOldExporterAddress2(String oldExporterAddress2) {
			this.oldExporterAddress2 = oldExporterAddress2;
		}

		public String getNewExporterAddress2() {
			return newExporterAddress2;
		}

		public void setNewExporterAddress2(String newExporterAddress2) {
			this.newExporterAddress2 = newExporterAddress2;
		}

		public String getOldExporterAddress3() {
			return oldExporterAddress3;
		}

		public void setOldExporterAddress3(String oldExporterAddress3) {
			this.oldExporterAddress3 = oldExporterAddress3;
		}

		public String getNewExporterAddress3() {
			return newExporterAddress3;
		}

		public void setNewExporterAddress3(String newExporterAddress3) {
			this.newExporterAddress3 = newExporterAddress3;
		}

		public String getOldSrNo() {
			return oldSrNo;
		}

		public void setOldSrNo(String oldSrNo) {
			this.oldSrNo = oldSrNo;
		}

		public String getNewSrNo() {
			return newSrNo;
		}

		public void setNewSrNo(String newSrNo) {
			this.newSrNo = newSrNo;
		}

		public String getOldGstNo() {
			return oldGstNo;
		}

		public void setOldGstNo(String oldGstNo) {
			this.oldGstNo = oldGstNo;
		}

		public String getNewGstNo() {
			return newGstNo;
		}

		public void setNewGstNo(String newGstNo) {
			this.newGstNo = newGstNo;
		}

		public String getOldState() {
			return oldState;
		}

		public void setOldState(String oldState) {
			this.oldState = oldState;
		}

		public String getNewState() {
			return newState;
		}

		public void setNewState(String newState) {
			this.newState = newState;
		}

		public String getOldIecCode() {
			return oldIecCode;
		}

		public void setOldIecCode(String oldIecCode) {
			this.oldIecCode = oldIecCode;
		}

		public String getNewIecCode() {
			return newIecCode;
		}

		public void setNewIecCode(String newIecCode) {
			this.newIecCode = newIecCode;
		}

		@Override
		public String toString() {
			return "ShippingBillDTO [sbtransid=" + sbtransid + ", auditremarks=" + auditremarks + ", profitCentreId="
					+ profitCentreId + ", companyId=" + companyId + ", branchId=" + branchId + ", finYear=" + finYear
					+ ", userId=" + userId + ", oldSbNo=" + oldSbNo + ", newSbNo=" + newSbNo + ", oldSbDate="
					+ oldSbDate + ", newSbDate=" + newSbDate + ", oldNoOfPackages=" + oldNoOfPackages
					+ ", newNoOfPackages=" + newNoOfPackages + ", oldGrossWeight=" + oldGrossWeight
					+ ", newGrossWeight=" + newGrossWeight + ", oldPackagesType=" + oldPackagesType
					+ ", newPackagesType=" + newPackagesType + ", oldExporter=" + oldExporter + ", newExporter="
					+ newExporter + ", oldCargoType=" + oldCargoType + ", newCargoType=" + newCargoType + ", oldFOB="
					+ oldFOB + ", newFOB=" + newFOB + ", oldCommodityId=" + oldCommodityId + ", newCommodityId="
					+ newCommodityId + ", oldNoOfMarks=" + oldNoOfMarks + ", newNoOfMarks=" + newNoOfMarks
					+ ", oldConsigneeName=" + oldConsigneeName + ", newConsigneeName=" + newConsigneeName + ", oldCHA="
					+ oldCHA + ", newCHA=" + newCHA + ", chaName=" + chaName + ", oldOnAcc=" + oldOnAcc + ", newOnAcc="
					+ newOnAcc + ", onAccountName=" + onAccountName + "]";
		}
		
		
		
		
		
//	SB Search Query	
	public ShippingBillDTO(BigDecimal noOfPackages, BigDecimal grossWeight,
			String typeOfPackage, BigDecimal fob, String sbNo, Date sbDate, String sbType,
			String sbTransId, String cargoType, String numberOfMarks,
			String commodity, String exporterName, String consigneeName, String cha, String chaName,
			String onAccountOf, String onAccountName, String exporterId,
			String exporterAddress1, String exporterAddress2, String exporterAddress3, String srNo, String gstNo,
			String state, String iecCode, String finYear		
			) {
		super();
		this.finYear = finYear;
		this.oldNoOfPackages = noOfPackages;
		this.oldGrossWeight = grossWeight;
		this.oldPackagesType  =typeOfPackage;
		this.oldFOB = fob;
		this.oldSbNo = sbNo;
		this.oldSbDate = sbDate;
		this.oldSbType = sbType;
		
		this.newNoOfPackages = noOfPackages;
		this.newGrossWeight = grossWeight;
		this.newPackagesType  =typeOfPackage;
		this.newFOB = fob;
		this.newSbNo = sbNo;
		this.newSbDate = sbDate;
		this.newSbType = sbType;		
		
		this.sbtransid = sbTransId;
		
		this.oldCargoType = cargoType;
		this.oldNoOfMarks = numberOfMarks;	
		this.oldCommodityId = commodity;
		this.oldExporter = exporterName;
		this.oldExporterId = exporterId;
		this.oldConsigneeName = consigneeName;
		this.oldCHA = cha;
		this.chaName = chaName;
		
		this.newCargoType = cargoType;
		this.newNoOfMarks = numberOfMarks;	
		this.newCommodityId = commodity;
		this.newExporter = exporterName;
		this.newExporterId = exporterId;
		this.newConsigneeName = consigneeName;
		this.newCHA = cha;
		this.oldOnAcc = onAccountOf;
		
		this.newOnAcc = onAccountOf;
		this.onAccountName = onAccountName;	
		
		
		
		 // Assign new fields for old and new values
	    this.oldExporterAddress1 = exporterAddress1;
	    this.newExporterAddress1 = exporterAddress1;

	    this.oldExporterAddress2 = exporterAddress2;
	    this.newExporterAddress2 = exporterAddress2;

	    this.oldExporterAddress3 = exporterAddress3;
	    this.newExporterAddress3 = exporterAddress3;

	    this.oldSrNo = srNo;
	    this.newSrNo = srNo;

	    this.oldGstNo = gstNo;
	    this.newGstNo = gstNo;

	    this.oldState = state;
	    this.newState = state;

	    this.oldIecCode = iecCode;
	    this.newIecCode = iecCode;
		
	    this.auditremarks = "";
		
	}

}
