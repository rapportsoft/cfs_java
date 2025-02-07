package com.cwms.entities;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name="cfigm")
@IdClass(CFIgmId.class)
public class CFIgm {

	@Id
    @Column(name = "Company_Id", length = 6, nullable = false)
    private String companyId;

    @Id
    @Column(name = "Branch_Id", length = 6, nullable = false)
    private String branchId;

    @Id
    @Column(name = "Fin_Year", length = 4, nullable = false)
    private String finYear;

    @Id
    @Column(name = "IGM_Trans_Id", length = 10, nullable = false)
    private String igmTransId;

    @Id
    @Column(name = "Profitcentre_Id", length = 6, nullable = false)
    private String profitcentreId;

    @Id
    @Column(name = "IGM_No", length = 10, nullable = false)
    private String igmNo;

    @Column(name = "Doc_Date")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date docDate = new Date(0);

    @Column(name = "IGM_Date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date igmDate = new Date(0);

    @Column(name = "VIA_NO", length = 10, nullable = false)
    private String viaNo = "";

    @Column(name = "Vessel_Id", length = 7, nullable = false)
    private String vesselId = "";

    @Column(name = "Voyage_No", length = 10, nullable = false)
    private String voyageNo = "";

    @Column(name = "Port", length = 6, nullable = false)
    private String port = "";

    @Column(name = "Vessel_ETA", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date vesselEta = new Date(0);

    @Column(name = "Vessel_Arv_Date")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date vesselArvDate = new Date(0);

    @Column(name = "Vessel_Arv_Time", length = 100)
    private String vesselArvTime = "";

    @Column(name = "Shipping_line", length = 6, nullable = false)
    private String shippingLine = "";

    @Column(name = "Shipping_Agent", length = 6, nullable = false)
    private String shippingAgent = "";

    @Column(name = "Comments", length = 150)
    private String comments;

    @Column(name = "Port_JO", length = 10)
    private String portJo;

    @Column(name = "Port_JO_Date")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date portJoDate = new Date(0);

    @Column(name = "Party_Id", length = 6)
    private String partyId;

    @Column(name = "Data_Input_Status", length = 1, nullable = false)
    private char dataInputStatus = 'N';

    @Column(name = "Export_JO_FileName", columnDefinition = "TEXT")
    private String exportJoFileName;

    @Column(name = "Entry_Status", length = 3, nullable = false)
    private String entryStatus = "";

    @Column(name = "Status", length = 1, nullable = false)
    private char status = 'N';

    @Column(name = "Created_By", length = 10, nullable = false)
    private String createdBy = "";

    @Column(name = "Created_Date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date createdDate = new Date(0);

    @Column(name = "Edited_By", length = 10)
    private String editedBy;

    @Column(name = "Edited_Date")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date editedDate = new Date(0);

    @Column(name = "Approved_By", length = 10)
    private String approvedBy;

    @Column(name = "Approved_Date")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date approvedDate = new Date(0);

    @Column(name = "Scanning_date")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Date scanningDate = new Date(0);

    @Column(name = "manual_link_flag", length = 1)
    private char manualLinkFlag = 'N';

	public CFIgm() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CFIgm(String companyId, String branchId, String finYear, String igmTransId, String profitcentreId,
			String igmNo, Date docDate, Date igmDate, String viaNo, String vesselId, String voyageNo, String port,
			Date vesselEta, Date vesselArvDate, String vesselArvTime, String shippingLine, String shippingAgent,
			String comments, String portJo, Date portJoDate, String partyId, char dataInputStatus,
			String exportJoFileName, String entryStatus, char status, String createdBy, Date createdDate,
			String editedBy, Date editedDate, String approvedBy, Date approvedDate, Date scanningDate,
			char manualLinkFlag) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.finYear = finYear;
		this.igmTransId = igmTransId;
		this.profitcentreId = profitcentreId;
		this.igmNo = igmNo;
		this.docDate = docDate;
		this.igmDate = igmDate;
		this.viaNo = viaNo;
		this.vesselId = vesselId;
		this.voyageNo = voyageNo;
		this.port = port;
		this.vesselEta = vesselEta;
		this.vesselArvDate = vesselArvDate;
		this.vesselArvTime = vesselArvTime;
		this.shippingLine = shippingLine;
		this.shippingAgent = shippingAgent;
		this.comments = comments;
		this.portJo = portJo;
		this.portJoDate = portJoDate;
		this.partyId = partyId;
		this.dataInputStatus = dataInputStatus;
		this.exportJoFileName = exportJoFileName;
		this.entryStatus = entryStatus;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.editedBy = editedBy;
		this.editedDate = editedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.scanningDate = scanningDate;
		this.manualLinkFlag = manualLinkFlag;
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

	public String getIgmTransId() {
		return igmTransId;
	}

	public void setIgmTransId(String igmTransId) {
		this.igmTransId = igmTransId;
	}

	public String getProfitcentreId() {
		return profitcentreId;
	}

	public void setProfitcentreId(String profitcentreId) {
		this.profitcentreId = profitcentreId;
	}

	public String getIgmNo() {
		return igmNo;
	}

	public void setIgmNo(String igmNo) {
		this.igmNo = igmNo;
	}

	public Date getDocDate() {
		return docDate;
	}

	public void setDocDate(Date docDate) {
		this.docDate = docDate;
	}

	public Date getIgmDate() {
		return igmDate;
	}

	public void setIgmDate(Date igmDate) {
		this.igmDate = igmDate;
	}

	public String getViaNo() {
		return viaNo;
	}

	public void setViaNo(String viaNo) {
		this.viaNo = viaNo;
	}

	public String getVesselId() {
		return vesselId;
	}

	public void setVesselId(String vesselId) {
		this.vesselId = vesselId;
	}

	public String getVoyageNo() {
		return voyageNo;
	}

	public void setVoyageNo(String voyageNo) {
		this.voyageNo = voyageNo;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public Date getVesselEta() {
		return vesselEta;
	}

	public void setVesselEta(Date vesselEta) {
		this.vesselEta = vesselEta;
	}

	public Date getVesselArvDate() {
		return vesselArvDate;
	}

	public void setVesselArvDate(Date vesselArvDate) {
		this.vesselArvDate = vesselArvDate;
	}

	public String getVesselArvTime() {
		return vesselArvTime;
	}

	public void setVesselArvTime(String vesselArvTime) {
		this.vesselArvTime = vesselArvTime;
	}

	public String getShippingLine() {
		return shippingLine;
	}

	public void setShippingLine(String shippingLine) {
		this.shippingLine = shippingLine;
	}

	public String getShippingAgent() {
		return shippingAgent;
	}

	public void setShippingAgent(String shippingAgent) {
		this.shippingAgent = shippingAgent;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getPortJo() {
		return portJo;
	}

	public void setPortJo(String portJo) {
		this.portJo = portJo;
	}

	public Date getPortJoDate() {
		return portJoDate;
	}

	public void setPortJoDate(Date portJoDate) {
		this.portJoDate = portJoDate;
	}

	public String getPartyId() {
		return partyId;
	}

	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}

	public char getDataInputStatus() {
		return dataInputStatus;
	}

	public void setDataInputStatus(char dataInputStatus) {
		this.dataInputStatus = dataInputStatus;
	}

	public String getExportJoFileName() {
		return exportJoFileName;
	}

	public void setExportJoFileName(String exportJoFileName) {
		this.exportJoFileName = exportJoFileName;
	}

	public String getEntryStatus() {
		return entryStatus;
	}

	public void setEntryStatus(String entryStatus) {
		this.entryStatus = entryStatus;
	}

	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
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

	public Date getScanningDate() {
		return scanningDate;
	}

	public void setScanningDate(Date scanningDate) {
		this.scanningDate = scanningDate;
	}

	public char getManualLinkFlag() {
		return manualLinkFlag;
	}

	public void setManualLinkFlag(char manualLinkFlag) {
		this.manualLinkFlag = manualLinkFlag;
	}

	public CFIgm(String igmTransId, String profitcentreId, String igmNo, Date igmDate, String viaNo, String voyageNo,
			String shippingLine, String shippingAgent) {
		super();
		this.igmTransId = igmTransId;
		this.profitcentreId = profitcentreId;
		this.igmNo = igmNo;
		this.igmDate = igmDate;
		this.viaNo = viaNo;
		this.voyageNo = voyageNo;
		this.shippingLine = shippingLine;
		this.shippingAgent = shippingAgent;
	}
    
    
	
	
	
    
    
	
}
