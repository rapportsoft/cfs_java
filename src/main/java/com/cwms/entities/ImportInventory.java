package com.cwms.entities;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@IdClass(ImportInventoryId.class)
@Table(name="cfimpinventory")
public class ImportInventory {
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
    @Column(name = "IGM_Trans_Id", length = 10)
    private String igmTransId;

    @Id
    @Column(name = "Profitcentre_Id", length = 6)
    private String profitcentreId;

    @Id
    @Column(name = "Igm_no", length = 10)
    private String igmNo;

    @Id
    @Column(name = "Container_No", length = 11)
    private String containerNo;

    @Id
    @Column(name = "Gate_In_Id", length = 10)
    private String gateInId;

    @Column(name = "Vessel_Id", length = 10)
    private String vesselId;

    @Column(name = "VIA_No", length = 10)
    private String viaNo;

    @Column(name = "Container_Size", length = 6)
    private String containerSize;

    @Column(name = "Container_Type", length = 6)
    private String containerType;

    @Column(name = "ISO", length = 4)
    private String iso;

    @Column(name = "Importer_Name", length = 80)
    private String importerName;

    @Column(name = "CHA", length = 6)
    private String cha;

    @Column(name = "SA", length = 6)
    private String sa;

    @Column(name = "SL", length = 6)
    private String sl;

    @Column(name = "Container_Status", length = 3)
    private String containerStatus;

    @Column(name = "Container_Seal_No", length = 15)
    private String containerSealNo;

    @Column(name = "Scanner_Type", length = 60)
    private String scannerType;

    @Column(name = "Seal_Cut_Trans_Id", length = 25)
    private String sealCutTransId;

    @Column(name = "Seal_Cut_Req_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sealCutReqDate;

    @Column(name = "Container_Exam_Status", length = 1)
    private String containerExamStatus;

    @Column(name = "Container_Exam_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date containerExamDate;

    @Column(name = "Yard_Location", length = 20)
    private String yardLocation;

    @Column(name = "Yard_Location1", length = 20)
    private String yardLocation1;

    @Column(name = "Yard_Block", length = 6)
    private String yardBlock;

    @Column(name = "Block_Cell_No", length = 10)
    private String blockCellNo;

    @Column(name = "DeStuff_Id", length = 25)
    private String deStuffId;

    @Column(name = "DeStuff_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deStuffDate;

    @Column(name = "Gate_In_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date gateInDate;

    @Column(name = "Gate_Out_Id", length = 10)
    private String gateOutId;

    @Column(name = "Gate_Out_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date gateOutDate;

    @Column(name = "Hold_Status", length = 1)
    private String holdStatus;

    @Column(name = "Holding_Agent_Name", length = 35)
    private String holdingAgentName;

    @Column(name = "Hold_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date holdDate;

    @Column(name = "Release_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date releaseDate;

    @Column(name = "Hold_Remarks", columnDefinition = "TEXT")
    private String holdRemarks;

    @Column(name = "Release_Remarks", columnDefinition = "TEXT")
    private String releaseRemarks;

    @Column(name = "Release_User_Name", length = 35)
    private String releaseUserName;

    @Column(name = "Gate_Pass_No", length = 10)
    private String gatePassNo;

    @Column(name = "Gate_Pass_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date gatePassDate;

    @Column(name = "Empty_Pass_Id", length = 10)
    private String emptyPassId;

    @Column(name = "Empty_Pass_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date emptyPassDate;

    @Column(name = "Empty_Out_Id", length = 10)
    private String emptyOutId;

    @Column(name = "Empty_Out_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date emptyOutDate;

    @Column(name = "Status", length = 1)
    private String status;

    @Column(name = "Created_By", length = 10)
    private String createdBy;

    @Column(name = "Created_Date")
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

    @Column(name = "No_of_Item", columnDefinition = "int default 0")
    private int noOfItem;

    @Column(name = "Container_Weight", columnDefinition = "decimal(14,2) default 0.00")
    private BigDecimal containerWeight;

    @Column(name = "Cycle", length = 10, columnDefinition = "varchar(10) default 'IMP'")
    private String cycle;

    @Column(name = "DO_Entry_Flag", length = 1, columnDefinition = "char(1) default 'N'")
    private String doEntryFlag;

    @Column(name = "DO_Entry_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date doEntryDate;

    @Column(name = "DocRefNo", length = 35)
    private String docRefNo;
    
    @Column(name = "Assessment_Id", length = 10)
    private String assessmentId;
    
    
    

	public String getAssessmentId() {
		return assessmentId;
	}

	public void setAssessmentId(String assessmentId) {
		this.assessmentId = assessmentId;
	}

	public void setNoOfItem(int noOfItem) {
		this.noOfItem = noOfItem;
	}

	public ImportInventory() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	

	public ImportInventory(String companyId, String branchId, String finYear, String igmTransId, String profitcentreId,
			String igmNo, String containerNo, String gateInId, String vesselId, String viaNo, String containerSize,
			String containerType, String iso, String importerName, String cha, String sa, String sl,
			String containerStatus, String containerSealNo, String scannerType, String sealCutTransId,
			Date sealCutReqDate, String containerExamStatus, Date containerExamDate, String yardLocation,
			String yardLocation1, String yardBlock, String blockCellNo, String deStuffId, Date deStuffDate,
			Date gateInDate, String gateOutId, Date gateOutDate, String holdStatus, String holdingAgentName,
			Date holdDate, Date releaseDate, String holdRemarks, String releaseRemarks, String releaseUserName,
			String gatePassNo, Date gatePassDate, String emptyPassId, Date emptyPassDate, String emptyOutId,
			Date emptyOutDate, String status, String createdBy, Date createdDate, String editedBy, Date editedDate,
			String approvedBy, Date approvedDate, int noOfItem, BigDecimal containerWeight, String cycle,
			String doEntryFlag, Date doEntryDate, String docRefNo, String assessmentId) {
		this.companyId = companyId;
		this.branchId = branchId;
		this.finYear = finYear;
		this.igmTransId = igmTransId;
		this.profitcentreId = profitcentreId;
		this.igmNo = igmNo;
		this.containerNo = containerNo;
		this.gateInId = gateInId;
		this.vesselId = vesselId;
		this.viaNo = viaNo;
		this.containerSize = containerSize;
		this.containerType = containerType;
		this.iso = iso;
		this.importerName = importerName;
		this.cha = cha;
		this.sa = sa;
		this.sl = sl;
		this.containerStatus = containerStatus;
		this.containerSealNo = containerSealNo;
		this.scannerType = scannerType;
		this.sealCutTransId = sealCutTransId;
		this.sealCutReqDate = sealCutReqDate;
		this.containerExamStatus = containerExamStatus;
		this.containerExamDate = containerExamDate;
		this.yardLocation = yardLocation;
		this.yardLocation1 = yardLocation1;
		this.yardBlock = yardBlock;
		this.blockCellNo = blockCellNo;
		this.deStuffId = deStuffId;
		this.deStuffDate = deStuffDate;
		this.gateInDate = gateInDate;
		this.gateOutId = gateOutId;
		this.gateOutDate = gateOutDate;
		this.holdStatus = holdStatus;
		this.holdingAgentName = holdingAgentName;
		this.holdDate = holdDate;
		this.releaseDate = releaseDate;
		this.holdRemarks = holdRemarks;
		this.releaseRemarks = releaseRemarks;
		this.releaseUserName = releaseUserName;
		this.gatePassNo = gatePassNo;
		this.gatePassDate = gatePassDate;
		this.emptyPassId = emptyPassId;
		this.emptyPassDate = emptyPassDate;
		this.emptyOutId = emptyOutId;
		this.emptyOutDate = emptyOutDate;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.editedBy = editedBy;
		this.editedDate = editedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.noOfItem = noOfItem;
		this.containerWeight = containerWeight;
		this.cycle = cycle;
		this.doEntryFlag = doEntryFlag;
		this.doEntryDate = doEntryDate;
		this.docRefNo = docRefNo;
		this.assessmentId = assessmentId;
	}

	public ImportInventory(String companyId, String branchId, String finYear, String igmTransId, String profitcentreId,
			String igmNo, String containerNo, String gateInId, String vesselId, String viaNo, String containerSize,
			String containerType, String iso, String importerName, String cha, String sa, String sl,
			String containerStatus, String containerSealNo, String scannerType, String sealCutTransId,
			Date sealCutReqDate, String containerExamStatus, Date containerExamDate, String yardLocation,
			String yardLocation1, String yardBlock, String blockCellNo, String deStuffId, Date deStuffDate,
			Date gateInDate, String gateOutId, Date gateOutDate, String holdStatus, String holdingAgentName,
			Date holdDate, Date releaseDate, String holdRemarks, String releaseRemarks, String releaseUserName,
			String gatePassNo, Date gatePassDate, String emptyPassId, Date emptyPassDate, String emptyOutId,
			Date emptyOutDate, String status, String createdBy, Date createdDate, String editedBy, Date editedDate,
			String approvedBy, Date approvedDate, Integer noOfItem, BigDecimal containerWeight, String cycle,
			String doEntryFlag, Date doEntryDate, String docRefNo) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.finYear = finYear;
		this.igmTransId = igmTransId;
		this.profitcentreId = profitcentreId;
		this.igmNo = igmNo;
		this.containerNo = containerNo;
		this.gateInId = gateInId;
		this.vesselId = vesselId;
		this.viaNo = viaNo;
		this.containerSize = containerSize;
		this.containerType = containerType;
		this.iso = iso;
		this.importerName = importerName;
		this.cha = cha;
		this.sa = sa;
		this.sl = sl;
		this.containerStatus = containerStatus;
		this.containerSealNo = containerSealNo;
		this.scannerType = scannerType;
		this.sealCutTransId = sealCutTransId;
		this.sealCutReqDate = sealCutReqDate;
		this.containerExamStatus = containerExamStatus;
		this.containerExamDate = containerExamDate;
		this.yardLocation = yardLocation;
		this.yardLocation1 = yardLocation1;
		this.yardBlock = yardBlock;
		this.blockCellNo = blockCellNo;
		this.deStuffId = deStuffId;
		this.deStuffDate = deStuffDate;
		this.gateInDate = gateInDate;
		this.gateOutId = gateOutId;
		this.gateOutDate = gateOutDate;
		this.holdStatus = holdStatus;
		this.holdingAgentName = holdingAgentName;
		this.holdDate = holdDate;
		this.releaseDate = releaseDate;
		this.holdRemarks = holdRemarks;
		this.releaseRemarks = releaseRemarks;
		this.releaseUserName = releaseUserName;
		this.gatePassNo = gatePassNo;
		this.gatePassDate = gatePassDate;
		this.emptyPassId = emptyPassId;
		this.emptyPassDate = emptyPassDate;
		this.emptyOutId = emptyOutId;
		this.emptyOutDate = emptyOutDate;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.editedBy = editedBy;
		this.editedDate = editedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.noOfItem = noOfItem;
		this.containerWeight = containerWeight;
		this.cycle = cycle;
		this.doEntryFlag = doEntryFlag;
		this.doEntryDate = doEntryDate;
		this.docRefNo = docRefNo;
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

	public String getContainerNo() {
		return containerNo;
	}

	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
	}

	public String getGateInId() {
		return gateInId;
	}

	public void setGateInId(String gateInId) {
		this.gateInId = gateInId;
	}

	public String getVesselId() {
		return vesselId;
	}

	public void setVesselId(String vesselId) {
		this.vesselId = vesselId;
	}

	public String getViaNo() {
		return viaNo;
	}

	public void setViaNo(String viaNo) {
		this.viaNo = viaNo;
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

	public String getIso() {
		return iso;
	}

	public void setIso(String iso) {
		this.iso = iso;
	}

	public String getImporterName() {
		return importerName;
	}

	public void setImporterName(String importerName) {
		this.importerName = importerName;
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

	public String getContainerStatus() {
		return containerStatus;
	}

	public void setContainerStatus(String containerStatus) {
		this.containerStatus = containerStatus;
	}

	public String getContainerSealNo() {
		return containerSealNo;
	}

	public void setContainerSealNo(String containerSealNo) {
		this.containerSealNo = containerSealNo;
	}

	public String getScannerType() {
		return scannerType;
	}

	public void setScannerType(String scannerType) {
		this.scannerType = scannerType;
	}

	public String getSealCutTransId() {
		return sealCutTransId;
	}

	public void setSealCutTransId(String sealCutTransId) {
		this.sealCutTransId = sealCutTransId;
	}

	public Date getSealCutReqDate() {
		return sealCutReqDate;
	}

	public void setSealCutReqDate(Date sealCutReqDate) {
		this.sealCutReqDate = sealCutReqDate;
	}

	public String getContainerExamStatus() {
		return containerExamStatus;
	}

	public void setContainerExamStatus(String containerExamStatus) {
		this.containerExamStatus = containerExamStatus;
	}

	public Date getContainerExamDate() {
		return containerExamDate;
	}

	public void setContainerExamDate(Date containerExamDate) {
		this.containerExamDate = containerExamDate;
	}

	public String getYardLocation() {
		return yardLocation;
	}

	public void setYardLocation(String yardLocation) {
		this.yardLocation = yardLocation;
	}

	public String getYardLocation1() {
		return yardLocation1;
	}

	public void setYardLocation1(String yardLocation1) {
		this.yardLocation1 = yardLocation1;
	}

	public String getYardBlock() {
		return yardBlock;
	}

	public void setYardBlock(String yardBlock) {
		this.yardBlock = yardBlock;
	}

	public String getBlockCellNo() {
		return blockCellNo;
	}

	public void setBlockCellNo(String blockCellNo) {
		this.blockCellNo = blockCellNo;
	}

	public String getDeStuffId() {
		return deStuffId;
	}

	public void setDeStuffId(String deStuffId) {
		this.deStuffId = deStuffId;
	}

	public Date getDeStuffDate() {
		return deStuffDate;
	}

	public void setDeStuffDate(Date deStuffDate) {
		this.deStuffDate = deStuffDate;
	}

	public Date getGateInDate() {
		return gateInDate;
	}

	public void setGateInDate(Date gateInDate) {
		this.gateInDate = gateInDate;
	}

	public String getGateOutId() {
		return gateOutId;
	}

	public void setGateOutId(String gateOutId) {
		this.gateOutId = gateOutId;
	}

	public Date getGateOutDate() {
		return gateOutDate;
	}

	public void setGateOutDate(Date gateOutDate) {
		this.gateOutDate = gateOutDate;
	}

	public String getHoldStatus() {
		return holdStatus;
	}

	public void setHoldStatus(String holdStatus) {
		this.holdStatus = holdStatus;
	}

	public String getHoldingAgentName() {
		return holdingAgentName;
	}

	public void setHoldingAgentName(String holdingAgentName) {
		this.holdingAgentName = holdingAgentName;
	}

	public Date getHoldDate() {
		return holdDate;
	}

	public void setHoldDate(Date holdDate) {
		this.holdDate = holdDate;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getHoldRemarks() {
		return holdRemarks;
	}

	public void setHoldRemarks(String holdRemarks) {
		this.holdRemarks = holdRemarks;
	}

	public String getReleaseRemarks() {
		return releaseRemarks;
	}

	public void setReleaseRemarks(String releaseRemarks) {
		this.releaseRemarks = releaseRemarks;
	}

	public String getReleaseUserName() {
		return releaseUserName;
	}

	public void setReleaseUserName(String releaseUserName) {
		this.releaseUserName = releaseUserName;
	}

	public String getGatePassNo() {
		return gatePassNo;
	}

	public void setGatePassNo(String gatePassNo) {
		this.gatePassNo = gatePassNo;
	}

	public Date getGatePassDate() {
		return gatePassDate;
	}

	public void setGatePassDate(Date gatePassDate) {
		this.gatePassDate = gatePassDate;
	}

	public String getEmptyPassId() {
		return emptyPassId;
	}

	public void setEmptyPassId(String emptyPassId) {
		this.emptyPassId = emptyPassId;
	}

	public Date getEmptyPassDate() {
		return emptyPassDate;
	}

	public void setEmptyPassDate(Date emptyPassDate) {
		this.emptyPassDate = emptyPassDate;
	}

	public String getEmptyOutId() {
		return emptyOutId;
	}

	public void setEmptyOutId(String emptyOutId) {
		this.emptyOutId = emptyOutId;
	}

	public Date getEmptyOutDate() {
		return emptyOutDate;
	}

	public void setEmptyOutDate(Date emptyOutDate) {
		this.emptyOutDate = emptyOutDate;
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

	public Integer getNoOfItem() {
		return noOfItem;
	}

	public void setNoOfItem(Integer noOfItem) {
		this.noOfItem = noOfItem;
	}

	public BigDecimal getContainerWeight() {
		return containerWeight;
	}

	public void setContainerWeight(BigDecimal containerWeight) {
		this.containerWeight = containerWeight;
	}

	public String getCycle() {
		return cycle;
	}

	public void setCycle(String cycle) {
		this.cycle = cycle;
	}

	public String getDoEntryFlag() {
		return doEntryFlag;
	}

	public void setDoEntryFlag(String doEntryFlag) {
		this.doEntryFlag = doEntryFlag;
	}

	public Date getDoEntryDate() {
		return doEntryDate;
	}

	public void setDoEntryDate(Date doEntryDate) {
		this.doEntryDate = doEntryDate;
	}

	public String getDocRefNo() {
		return docRefNo;
	}

	public void setDocRefNo(String docRefNo) {
		this.docRefNo = docRefNo;
	}
    
    
    
}
