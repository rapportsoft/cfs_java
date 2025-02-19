package com.cwms.entities;

import jakarta.persistence.*;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@IdClass(CfstdtrfId.class)
@Table(name = "cfstdtrfquatation")
public class CfsTarrifQuatation implements Cloneable {
	@Id
    @Column(name = "Company_Id", length = 6)
    private String companyId;

    @Id
    @Column(name = "Branch_Id", length = 6)
    private String branchId;

    @Id
    @Column(name = "Fin_Year", length = 4)
    private String finYear;

    @Id
    @Column(name = "Profit_Centre_Id", length = 10)
    private String profitCentreId;

    @Id
    @Column(name = "CFS_Tariff_No", length = 10)
    private String cfsTariffNo;

    @Id
    @Column(name = "CFS_Amnd_No", length = 3)
    private String cfsAmndNo;

    @Id
    @Column(name = "Party_Id", length = 50)
    private String partyId;

    @Id
    @Column(name = "Status", length = 1)
    private String status;

    @Column(name = "Contract_Name", length = 250)
    private String contractName;

    @Column(name = "Shipping_Line", length = 6)
    private String shippingLine;

    @Column(name = "CHA", length = 6)
    private String cha;

    @Column(name = "Importer_Id", length = 6)
    private String importerId;

    @Column(name = "Exporter_Id", length = 6)
    private String exporterId;

    @Column(name = "Shipping_Agent", length = 6)
    private String shippingAgent;

    @Column(name = "Forwarder_Id", length = 6)
    private String forwarderId;

    @Column(name = "Consoler_Id", length = 6)
    private String consolerId;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CFS_Tariff_Date")
    private Date cfsTariffDate;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CFS_From_Date")
    private Date cfsFromDate;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CFS_Validate_Date")
    private Date cfsValidateDate;

    @Column(name = "File_Path", length = 140)
    private String filePath;

    @Column(name = "REF_Tariff_No", length = 10)
    private String refTariffNo;

    @Column(name = "Comments", length = 150)
    private String comments;

    @Column(name = "Created_By", length = 10)
    private String createdBy;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "Created_Date")
    private Date createdDate;

    @Column(name = "Edited_By", length = 10)
    private String editedBy;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "Edited_Date")
    private Date editedDate;

    @Column(name = "Approved_By", length = 10)
    private String approvedBy;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "Approved_Date")
    private Date approvedDate;

    @Column(name = "AmmendStatus", length = 1)
    private String ammendStatus;

    @Column(name = "nvocc_tariff", length = 1)
    private String nvoccTariff = "N";

    @Column(name = "offdoc_tariff", length = 1)
    private String offdocTariff = "N";

    @Column(name = "tariff_type", length = 12)
    private String tariffType = "Standard";

    @Column(name = "Ref_Tariff_Id", length = 10)
    private String refTariffId;

    @Column(name = "Ref_Tariff_Amnd_Id", length = 3)
    private String refTariffAmndId;
    
    transient private String partyName;
    transient private String shippingAgentName;
    transient private String shippingLineName;
    transient private String chaName;
    transient private String importerName;
    transient private String forwarderName;
    
    transient private String applyTariffNo;
    transient private String applyAmendNo;

    
    
    
    
    

	

	public String getApplyTariffNo() {
		return applyTariffNo;
	}







	public void setApplyTariffNo(String applyTariffNo) {
		this.applyTariffNo = applyTariffNo;
	}







	public String getApplyAmendNo() {
		return applyAmendNo;
	}







	public void setApplyAmendNo(String applyAmendNo) {
		this.applyAmendNo = applyAmendNo;
	}







	public String getPartyName() {
		return partyName;
	}







	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}







	public String getShippingAgentName() {
		return shippingAgentName;
	}







	public void setShippingAgentName(String shippingAgentName) {
		this.shippingAgentName = shippingAgentName;
	}







	public String getShippingLineName() {
		return shippingLineName;
	}







	public void setShippingLineName(String shippingLineName) {
		this.shippingLineName = shippingLineName;
	}







	public String getChaName() {
		return chaName;
	}







	public void setChaName(String chaName) {
		this.chaName = chaName;
	}







	public String getImporterName() {
		return importerName;
	}







	public void setImporterName(String importerName) {
		this.importerName = importerName;
	}







	public String getForwarderName() {
		return forwarderName;
	}







	public void setForwarderName(String forwarderName) {
		this.forwarderName = forwarderName;
	}







	public CfsTarrifQuatation(String companyId, String branchId, String finYear, String profitCentreId, String cfsTariffNo,
			String cfsAmndNo, String partyId, String status, String contractName, String shippingLine, String cha,
			String importerId, String exporterId, String shippingAgent, String forwarderId, String consolerId,
			Date cfsTariffDate, Date cfsFromDate, Date cfsValidateDate, String filePath, String refTariffNo,
			String comments, String createdBy, Date createdDate, String editedBy, Date editedDate, String approvedBy,
			Date approvedDate, String ammendStatus, String nvoccTariff, String offdocTariff, String tariffType,
			String refTariffId, String refTariffAmndId, String partyName, String shippingAgentName,
			String shippingLineName, String chaName, String importerName, String forwarderName) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.finYear = finYear;
		this.profitCentreId = profitCentreId;
		this.cfsTariffNo = cfsTariffNo;
		this.cfsAmndNo = cfsAmndNo;
		this.partyId = partyId;
		this.status = status;
		this.contractName = contractName;
		this.shippingLine = shippingLine;
		this.cha = cha;
		this.importerId = importerId;
		this.exporterId = exporterId;
		this.shippingAgent = shippingAgent;
		this.forwarderId = forwarderId;
		this.consolerId = consolerId;
		this.cfsTariffDate = cfsTariffDate;
		this.cfsFromDate = cfsFromDate;
		this.cfsValidateDate = cfsValidateDate;
		this.filePath = filePath;
		this.refTariffNo = refTariffNo;
		this.comments = comments;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.editedBy = editedBy;
		this.editedDate = editedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.ammendStatus = ammendStatus;
		this.nvoccTariff = nvoccTariff;
		this.offdocTariff = offdocTariff;
		this.tariffType = tariffType;
		this.refTariffId = refTariffId;
		this.refTariffAmndId = refTariffAmndId;
		this.partyName = partyName;
		this.shippingAgentName = shippingAgentName;
		this.shippingLineName = shippingLineName;
		this.chaName = chaName;
		this.importerName = importerName;
		this.forwarderName = forwarderName;
	}
	
	
	
	
	
	

	public CfsTarrifQuatation(String companyId, String branchId, String finYear, String profitCentreId, String cfsTariffNo,
			String cfsAmndNo, String partyId, String status, String contractName, String shippingLine, String cha,
			String importerId, String exporterId, String shippingAgent, String forwarderId, String consolerId,
			Date cfsTariffDate, Date cfsFromDate, Date cfsValidateDate, String filePath, String refTariffNo,
			String comments, String createdBy, Date createdDate, String editedBy, Date editedDate, String approvedBy,
			Date approvedDate, String ammendStatus, String nvoccTariff, String offdocTariff, String tariffType,
			String refTariffId, String refTariffAmndId) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.finYear = finYear;
		this.profitCentreId = profitCentreId;
		this.cfsTariffNo = cfsTariffNo;
		this.cfsAmndNo = cfsAmndNo;
		this.partyId = partyId;
		this.status = status;
		this.contractName = contractName;
		this.shippingLine = shippingLine;
		this.cha = cha;
		this.importerId = importerId;
		this.exporterId = exporterId;
		this.shippingAgent = shippingAgent;
		this.forwarderId = forwarderId;
		this.consolerId = consolerId;
		this.cfsTariffDate = cfsTariffDate;
		this.cfsFromDate = cfsFromDate;
		this.cfsValidateDate = cfsValidateDate;
		this.filePath = filePath;
		this.refTariffNo = refTariffNo;
		this.comments = comments;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.editedBy = editedBy;
		this.editedDate = editedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.ammendStatus = ammendStatus;
		this.nvoccTariff = nvoccTariff;
		this.offdocTariff = offdocTariff;
		this.tariffType = tariffType;
		this.refTariffId = refTariffId;
		this.refTariffAmndId = refTariffAmndId;
	}

	public CfsTarrifQuatation() {
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

	public String getProfitCentreId() {
		return profitCentreId;
	}

	public void setProfitCentreId(String profitCentreId) {
		this.profitCentreId = profitCentreId;
	}

	public String getCfsTariffNo() {
		return cfsTariffNo;
	}

	public void setCfsTariffNo(String cfsTariffNo) {
		this.cfsTariffNo = cfsTariffNo;
	}

	public String getCfsAmndNo() {
		return cfsAmndNo;
	}

	public void setCfsAmndNo(String cfsAmndNo) {
		this.cfsAmndNo = cfsAmndNo;
	}

	public String getPartyId() {
		return partyId;
	}

	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public String getShippingLine() {
		return shippingLine;
	}

	public void setShippingLine(String shippingLine) {
		this.shippingLine = shippingLine;
	}

	public String getCha() {
		return cha;
	}

	public void setCha(String cha) {
		this.cha = cha;
	}

	public String getImporterId() {
		return importerId;
	}

	public void setImporterId(String importerId) {
		this.importerId = importerId;
	}

	public String getExporterId() {
		return exporterId;
	}

	public void setExporterId(String exporterId) {
		this.exporterId = exporterId;
	}

	public String getShippingAgent() {
		return shippingAgent;
	}

	public void setShippingAgent(String shippingAgent) {
		this.shippingAgent = shippingAgent;
	}

	public String getForwarderId() {
		return forwarderId;
	}

	public void setForwarderId(String forwarderId) {
		this.forwarderId = forwarderId;
	}

	public String getConsolerId() {
		return consolerId;
	}

	public void setConsolerId(String consolerId) {
		this.consolerId = consolerId;
	}

	public Date getCfsTariffDate() {
		return cfsTariffDate;
	}

	public void setCfsTariffDate(Date cfsTariffDate) {
		this.cfsTariffDate = cfsTariffDate;
	}

	public Date getCfsFromDate() {
		return cfsFromDate;
	}

	public void setCfsFromDate(Date cfsFromDate) {
		this.cfsFromDate = cfsFromDate;
	}

	public Date getCfsValidateDate() {
		return cfsValidateDate;
	}

	public void setCfsValidateDate(Date cfsValidateDate) {
		this.cfsValidateDate = cfsValidateDate;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getRefTariffNo() {
		return refTariffNo;
	}

	public void setRefTariffNo(String refTariffNo) {
		this.refTariffNo = refTariffNo;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
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

	public String getAmmendStatus() {
		return ammendStatus;
	}

	public void setAmmendStatus(String ammendStatus) {
		this.ammendStatus = ammendStatus;
	}

	public String getNvoccTariff() {
		return nvoccTariff;
	}

	public void setNvoccTariff(String nvoccTariff) {
		this.nvoccTariff = nvoccTariff;
	}

	public String getOffdocTariff() {
		return offdocTariff;
	}

	public void setOffdocTariff(String offdocTariff) {
		this.offdocTariff = offdocTariff;
	}

	public String getTariffType() {
		return tariffType;
	}

	public void setTariffType(String tariffType) {
		this.tariffType = tariffType;
	}

	public String getRefTariffId() {
		return refTariffId;
	}

	public void setRefTariffId(String refTariffId) {
		this.refTariffId = refTariffId;
	}

	public String getRefTariffAmndId() {
		return refTariffAmndId;
	}

	public void setRefTariffAmndId(String refTariffAmndId) {
		this.refTariffAmndId = refTariffAmndId;
	}

	public CfsTarrifQuatation(String cfsTariffNo, String cfsAmndNo, String contractName) {
		super();
		this.cfsTariffNo = cfsTariffNo;
		this.cfsAmndNo = cfsAmndNo;
		this.contractName = contractName;
	}

	@Override
	public String toString() {
		return "CfsTarrif [companyId=" + companyId + ", branchId=" + branchId + ", finYear=" + finYear
				+ ", profitCentreId=" + profitCentreId + ", cfsTariffNo=" + cfsTariffNo + ", cfsAmndNo=" + cfsAmndNo
				+ ", partyId=" + partyId + ", status=" + status + ", contractName=" + contractName + ", shippingLine="
				+ shippingLine + ", cha=" + cha + ", importerId=" + importerId + ", exporterId=" + exporterId
				+ ", shippingAgent=" + shippingAgent + ", forwarderId=" + forwarderId + ", consolerId=" + consolerId
				+ ", cfsTariffDate=" + cfsTariffDate + ", cfsFromDate=" + cfsFromDate + ", cfsValidateDate="
				+ cfsValidateDate + ", filePath=" + filePath + ", refTariffNo=" + refTariffNo + ", comments=" + comments
				+ ", createdBy=" + createdBy + ", createdDate=" + createdDate + ", editedBy=" + editedBy
				+ ", editedDate=" + editedDate + ", approvedBy=" + approvedBy + ", approvedDate=" + approvedDate
				+ ", ammendStatus=" + ammendStatus + ", nvoccTariff=" + nvoccTariff + ", offdocTariff=" + offdocTariff
				+ ", tariffType=" + tariffType + ", refTariffId=" + refTariffId + ", refTariffAmndId=" + refTariffAmndId
				+ "]";
	}

	public CfsTarrifQuatation(String cfsTariffNo, String cfsAmndNo, String status, String ammendStatus) {
		super();
		this.cfsTariffNo = cfsTariffNo;
		this.cfsAmndNo = cfsAmndNo;
		this.status = status;
		this.ammendStatus = ammendStatus;
	}	
	
	
	@Override
	public Object clone() throws CloneNotSupportedException {
	    return super.clone();
	}







	public CfsTarrifQuatation(String partyId, String status, String contractName, String shippingLine, String cha,
			String importerId, String exporterId, String shippingAgent, String forwarderId, String consolerId,
			Date cfsTariffDate, Date cfsFromDate, Date cfsValidateDate) {
		super();
		this.partyId = partyId;
		this.status = status;
		this.contractName = contractName;
		this.shippingLine = shippingLine;
		this.cha = cha;
		this.importerId = importerId;
		this.exporterId = exporterId;
		this.shippingAgent = shippingAgent;
		this.forwarderId = forwarderId;
		this.consolerId = consolerId;
		this.cfsTariffDate = cfsTariffDate;
		this.cfsFromDate = cfsFromDate;
		this.cfsValidateDate = cfsValidateDate;
	}





// AuditTrail Report

	public CfsTarrifQuatation(String cfsTariffNo, String cfsAmndNo, String contractName, String partyName,
			String shippingAgentName, String shippingLineName, String chaName, String importerName,
			String forwarderName) {
		super();
		this.cfsTariffNo = cfsTariffNo;
		this.cfsAmndNo = cfsAmndNo;
		this.contractName = contractName;
		this.partyName = partyName;
		this.shippingAgentName = shippingAgentName;
		this.shippingLineName = shippingLineName;
		this.chaName = chaName;
		this.importerName = importerName;
		this.forwarderName = forwarderName;
	}
	
	
	
	
	
	
	
	
	
}