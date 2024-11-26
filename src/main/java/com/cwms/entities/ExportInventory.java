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
@IdClass(ExportInventoryId.class)
@Table(name = "cfexpinventory")
public class ExportInventory {

    @Id
    @Column(name = "Company_Id", length = 6)
    private String companyId;

    @Id
    @Column(name = "Branch_Id", length = 6)
    private String branchId;

    @Id
    @Column(name = "SB_Trans_Id", length = 10)
    private String sbTransId;

    @Id
    @Column(name = "Profitcentre_Id", length = 6)
    private String profitcentreId;

    @Id
    @Column(name = "SB_no", length = 10)
    private String sbNo;

    @Id
    @Column(name = "Container_No", length = 11)
    private String containerNo;

    @Id
    @Column(name = "Gate_In_Id", length = 10)
    private String gateInId;

    @Column(name = "Vessel_Id", length = 7)
    private String vesselId;

    @Column(name = "VIA_No", length = 10)
    private String viaNo;

    @Column(name = "Container_Size", length = 6)
    private String containerSize;

    @Column(name = "Container_Type", length = 6)
    private String containerType;

    @Column(name = "ISO", length = 4)
    private String iso;

    @Column(name = "Exporter_Name", length = 80)
    private String exporterName;

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

    @Column(name = "E_Booking_No", length = 60)
    private String eBookingNo;

    @Column(name = "Stuff_req_Id", length = 10)
    private String stuffReqId;

    @Column(name = "Stuff_Req_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date stuffReqDate;

    @Column(name = "Stuff_Tally_Id", length = 10)
    private String stuffTallyId;

    @Column(name = "Stuff_Tally_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date stuffTallyDate;

    @Column(name = "Yard_Location", length = 6)
    private String yardLocation;

    @Column(name = "Yard_Block", length = 6)
    private String yardBlock;

    @Column(name = "Block_Cell_No", length = 10)
    private String blockCellNo;

    @Column(name = "Movement_req_Id", length = 10)
    private String movementReqId;

    @Column(name = "Movement_req_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date movementReqDate;

    @Column(name = "Back_To_Town_Id", length = 10)
    private String backToTownId;

    @Column(name = "Back_To_Town_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date backToTownDate;

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

    @Column(name = "Hold_Remarks", columnDefinition = "text")
    private String holdRemarks;

    @Column(name = "Release_Remarks", columnDefinition = "text")
    private String releaseRemarks;

    @Column(name = "Release_User_Name", length = 35)
    private String releaseUserName;

    @Column(name = "Gate_Pass_No", length = 10)
    private String gatePassNo;

    @Column(name = "Gate_Pass_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date gatePassDate;

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

    @Column(name = "No_of_Item")
    private int noOfItem;

    @Column(name = "Empty_Pass_Id", length = 10)
    private String emptyPassId;

    @Column(name = "Empty_Pass_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date emptyPassDate;

    @Column(name = "Empty_Gateout_Id", length = 10)
    private String emptyGateoutId;

    @Column(name = "Empty_Gateout_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date emptyGateoutDate;

    @Column(name = "Container_Weight", precision = 15, scale = 3)
    private BigDecimal containerWeight;

    @Column(name = "Cycle", length = 10)
    private String cycle;

    @Column(name = "DocRefNo", length = 35)
    private String docRefNo;

    @Column(name = "hold_Edited_By", length = 10)
    private String holdEditedBy;

    @Column(name = "hold_Edited_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date holdEditedDate;

    @Column(name = "GatePass_Edited_By", length = 10)
    private String gatePassEditedBy;

    @Column(name = "GatePass_Edited_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date gatePassEditedDate;

    @Column(name = "Job_Edited_By", length = 10)
    private String jobEditedBy;

    @Column(name = "Job_Edited_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date jobEditedDate;

    @Column(name = "GateIn_Edited_By", length = 10)
    private String gateInEditedBy;

    @Column(name = "GateIn_Edited_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date gateInEditedDate;

    @Column(name = "GateOut_Edited_By", length = 10)
    private String gateOutEditedBy;

    @Column(name = "GateOut_Edited_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date gateOutEditedDate;

    @Column(name = "Mty_Out_Edited_By", length = 10)
    private String mtyOutEditedBy;

    @Column(name = "Mty_Out_Edited_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date mtyOutEditedDate;

    @Column(name = "Mty_In_Edited_By", length = 10)
    private String mtyInEditedBy;

    @Column(name = "Mty_In_Edited_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date mtyInEditedDate;

    @Column(name = "HUB_Stf_Edited_By", length = 10)
    private String hubStfEditedBy;

    @Column(name = "HUB_Stf_Edited_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date hubStfEditedDate;

    @Column(name = "HUB_GatePass_Edited_By", length = 10)
    private String hubGatePassEditedBy;

    @Column(name = "HUB_GatePass_Edited_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date hubGatePassEditedDate;

    @Column(name = "HUB_Port_GateIn_Edited_By", length = 10)
    private String hubPortGateInEditedBy;

    @Column(name = "HUB_Port_GateIn_Edited_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date hubPortGateInEditedDate;

    @Column(name = "BTT_Edited_By", length = 10)
    private String bttEditedBy;

    @Column(name = "BTT_Edited_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date bttEditedDate;

    @Column(name = "Movement_Edited_By", length = 10)
    private String movementEditedBy;

    @Column(name = "Movement_Edited_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date movementEditedDate;

    @Column(name = "StuffReq_Edited_By", length = 10)
    private String stuffReqEditedBy;

    @Column(name = "StuffReq_Edited_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date stuffReqEditedDate;

    @Column(name = "Stufftally_Edited_By", length = 10)
    private String stuffTallyEditedBy;

    @Column(name = "Stufftally_Edited_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date stuffTallyEditedDate;

	public ExportInventory() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ExportInventory(String companyId, String branchId, String sbTransId, String profitcentreId, String sbNo,
			String containerNo, String gateInId, String vesselId, String viaNo, String containerSize,
			String containerType, String iso, String exporterName, String cha, String sa, String sl,
			String containerStatus, String containerSealNo, String eBookingNo, String stuffReqId, Date stuffReqDate,
			String stuffTallyId, Date stuffTallyDate, String yardLocation, String yardBlock, String blockCellNo,
			String movementReqId, Date movementReqDate, String backToTownId, Date backToTownDate, Date gateInDate,
			String gateOutId, Date gateOutDate, String holdStatus, String holdingAgentName, Date holdDate,
			Date releaseDate, String holdRemarks, String releaseRemarks, String releaseUserName, String gatePassNo,
			Date gatePassDate, String status, String createdBy, Date createdDate, String editedBy, Date editedDate,
			String approvedBy, Date approvedDate, int noOfItem, String emptyPassId, Date emptyPassDate,
			String emptyGateoutId, Date emptyGateoutDate, BigDecimal containerWeight, String cycle, String docRefNo,
			String holdEditedBy, Date holdEditedDate, String gatePassEditedBy, Date gatePassEditedDate,
			String jobEditedBy, Date jobEditedDate, String gateInEditedBy, Date gateInEditedDate,
			String gateOutEditedBy, Date gateOutEditedDate, String mtyOutEditedBy, Date mtyOutEditedDate,
			String mtyInEditedBy, Date mtyInEditedDate, String hubStfEditedBy, Date hubStfEditedDate,
			String hubGatePassEditedBy, Date hubGatePassEditedDate, String hubPortGateInEditedBy,
			Date hubPortGateInEditedDate, String bttEditedBy, Date bttEditedDate, String movementEditedBy,
			Date movementEditedDate, String stuffReqEditedBy, Date stuffReqEditedDate, String stuffTallyEditedBy,
			Date stuffTallyEditedDate) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.sbTransId = sbTransId;
		this.profitcentreId = profitcentreId;
		this.sbNo = sbNo;
		this.containerNo = containerNo;
		this.gateInId = gateInId;
		this.vesselId = vesselId;
		this.viaNo = viaNo;
		this.containerSize = containerSize;
		this.containerType = containerType;
		this.iso = iso;
		this.exporterName = exporterName;
		this.cha = cha;
		this.sa = sa;
		this.sl = sl;
		this.containerStatus = containerStatus;
		this.containerSealNo = containerSealNo;
		this.eBookingNo = eBookingNo;
		this.stuffReqId = stuffReqId;
		this.stuffReqDate = stuffReqDate;
		this.stuffTallyId = stuffTallyId;
		this.stuffTallyDate = stuffTallyDate;
		this.yardLocation = yardLocation;
		this.yardBlock = yardBlock;
		this.blockCellNo = blockCellNo;
		this.movementReqId = movementReqId;
		this.movementReqDate = movementReqDate;
		this.backToTownId = backToTownId;
		this.backToTownDate = backToTownDate;
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
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.editedBy = editedBy;
		this.editedDate = editedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.noOfItem = noOfItem;
		this.emptyPassId = emptyPassId;
		this.emptyPassDate = emptyPassDate;
		this.emptyGateoutId = emptyGateoutId;
		this.emptyGateoutDate = emptyGateoutDate;
		this.containerWeight = containerWeight;
		this.cycle = cycle;
		this.docRefNo = docRefNo;
		this.holdEditedBy = holdEditedBy;
		this.holdEditedDate = holdEditedDate;
		this.gatePassEditedBy = gatePassEditedBy;
		this.gatePassEditedDate = gatePassEditedDate;
		this.jobEditedBy = jobEditedBy;
		this.jobEditedDate = jobEditedDate;
		this.gateInEditedBy = gateInEditedBy;
		this.gateInEditedDate = gateInEditedDate;
		this.gateOutEditedBy = gateOutEditedBy;
		this.gateOutEditedDate = gateOutEditedDate;
		this.mtyOutEditedBy = mtyOutEditedBy;
		this.mtyOutEditedDate = mtyOutEditedDate;
		this.mtyInEditedBy = mtyInEditedBy;
		this.mtyInEditedDate = mtyInEditedDate;
		this.hubStfEditedBy = hubStfEditedBy;
		this.hubStfEditedDate = hubStfEditedDate;
		this.hubGatePassEditedBy = hubGatePassEditedBy;
		this.hubGatePassEditedDate = hubGatePassEditedDate;
		this.hubPortGateInEditedBy = hubPortGateInEditedBy;
		this.hubPortGateInEditedDate = hubPortGateInEditedDate;
		this.bttEditedBy = bttEditedBy;
		this.bttEditedDate = bttEditedDate;
		this.movementEditedBy = movementEditedBy;
		this.movementEditedDate = movementEditedDate;
		this.stuffReqEditedBy = stuffReqEditedBy;
		this.stuffReqEditedDate = stuffReqEditedDate;
		this.stuffTallyEditedBy = stuffTallyEditedBy;
		this.stuffTallyEditedDate = stuffTallyEditedDate;
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

	public String getSbTransId() {
		return sbTransId;
	}

	public void setSbTransId(String sbTransId) {
		this.sbTransId = sbTransId;
	}

	public String getProfitcentreId() {
		return profitcentreId;
	}

	public void setProfitcentreId(String profitcentreId) {
		this.profitcentreId = profitcentreId;
	}

	public String getSbNo() {
		return sbNo;
	}

	public void setSbNo(String sbNo) {
		this.sbNo = sbNo;
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

	public String getExporterName() {
		return exporterName;
	}

	public void setExporterName(String exporterName) {
		this.exporterName = exporterName;
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

	public String geteBookingNo() {
		return eBookingNo;
	}

	public void seteBookingNo(String eBookingNo) {
		this.eBookingNo = eBookingNo;
	}

	public String getStuffReqId() {
		return stuffReqId;
	}

	public void setStuffReqId(String stuffReqId) {
		this.stuffReqId = stuffReqId;
	}

	public Date getStuffReqDate() {
		return stuffReqDate;
	}

	public void setStuffReqDate(Date stuffReqDate) {
		this.stuffReqDate = stuffReqDate;
	}

	public String getStuffTallyId() {
		return stuffTallyId;
	}

	public void setStuffTallyId(String stuffTallyId) {
		this.stuffTallyId = stuffTallyId;
	}

	public Date getStuffTallyDate() {
		return stuffTallyDate;
	}

	public void setStuffTallyDate(Date stuffTallyDate) {
		this.stuffTallyDate = stuffTallyDate;
	}

	public String getYardLocation() {
		return yardLocation;
	}

	public void setYardLocation(String yardLocation) {
		this.yardLocation = yardLocation;
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

	public String getMovementReqId() {
		return movementReqId;
	}

	public void setMovementReqId(String movementReqId) {
		this.movementReqId = movementReqId;
	}

	public Date getMovementReqDate() {
		return movementReqDate;
	}

	public void setMovementReqDate(Date movementReqDate) {
		this.movementReqDate = movementReqDate;
	}

	public String getBackToTownId() {
		return backToTownId;
	}

	public void setBackToTownId(String backToTownId) {
		this.backToTownId = backToTownId;
	}

	public Date getBackToTownDate() {
		return backToTownDate;
	}

	public void setBackToTownDate(Date backToTownDate) {
		this.backToTownDate = backToTownDate;
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

	public int getNoOfItem() {
		return noOfItem;
	}

	public void setNoOfItem(int noOfItem) {
		this.noOfItem = noOfItem;
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

	public String getEmptyGateoutId() {
		return emptyGateoutId;
	}

	public void setEmptyGateoutId(String emptyGateoutId) {
		this.emptyGateoutId = emptyGateoutId;
	}

	public Date getEmptyGateoutDate() {
		return emptyGateoutDate;
	}

	public void setEmptyGateoutDate(Date emptyGateoutDate) {
		this.emptyGateoutDate = emptyGateoutDate;
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

	public String getDocRefNo() {
		return docRefNo;
	}

	public void setDocRefNo(String docRefNo) {
		this.docRefNo = docRefNo;
	}

	public String getHoldEditedBy() {
		return holdEditedBy;
	}

	public void setHoldEditedBy(String holdEditedBy) {
		this.holdEditedBy = holdEditedBy;
	}

	public Date getHoldEditedDate() {
		return holdEditedDate;
	}

	public void setHoldEditedDate(Date holdEditedDate) {
		this.holdEditedDate = holdEditedDate;
	}

	public String getGatePassEditedBy() {
		return gatePassEditedBy;
	}

	public void setGatePassEditedBy(String gatePassEditedBy) {
		this.gatePassEditedBy = gatePassEditedBy;
	}

	public Date getGatePassEditedDate() {
		return gatePassEditedDate;
	}

	public void setGatePassEditedDate(Date gatePassEditedDate) {
		this.gatePassEditedDate = gatePassEditedDate;
	}

	public String getJobEditedBy() {
		return jobEditedBy;
	}

	public void setJobEditedBy(String jobEditedBy) {
		this.jobEditedBy = jobEditedBy;
	}

	public Date getJobEditedDate() {
		return jobEditedDate;
	}

	public void setJobEditedDate(Date jobEditedDate) {
		this.jobEditedDate = jobEditedDate;
	}

	public String getGateInEditedBy() {
		return gateInEditedBy;
	}

	public void setGateInEditedBy(String gateInEditedBy) {
		this.gateInEditedBy = gateInEditedBy;
	}

	public Date getGateInEditedDate() {
		return gateInEditedDate;
	}

	public void setGateInEditedDate(Date gateInEditedDate) {
		this.gateInEditedDate = gateInEditedDate;
	}

	public String getGateOutEditedBy() {
		return gateOutEditedBy;
	}

	public void setGateOutEditedBy(String gateOutEditedBy) {
		this.gateOutEditedBy = gateOutEditedBy;
	}

	public Date getGateOutEditedDate() {
		return gateOutEditedDate;
	}

	public void setGateOutEditedDate(Date gateOutEditedDate) {
		this.gateOutEditedDate = gateOutEditedDate;
	}

	public String getMtyOutEditedBy() {
		return mtyOutEditedBy;
	}

	public void setMtyOutEditedBy(String mtyOutEditedBy) {
		this.mtyOutEditedBy = mtyOutEditedBy;
	}

	public Date getMtyOutEditedDate() {
		return mtyOutEditedDate;
	}

	public void setMtyOutEditedDate(Date mtyOutEditedDate) {
		this.mtyOutEditedDate = mtyOutEditedDate;
	}

	public String getMtyInEditedBy() {
		return mtyInEditedBy;
	}

	public void setMtyInEditedBy(String mtyInEditedBy) {
		this.mtyInEditedBy = mtyInEditedBy;
	}

	public Date getMtyInEditedDate() {
		return mtyInEditedDate;
	}

	public void setMtyInEditedDate(Date mtyInEditedDate) {
		this.mtyInEditedDate = mtyInEditedDate;
	}

	public String getHubStfEditedBy() {
		return hubStfEditedBy;
	}

	public void setHubStfEditedBy(String hubStfEditedBy) {
		this.hubStfEditedBy = hubStfEditedBy;
	}

	public Date getHubStfEditedDate() {
		return hubStfEditedDate;
	}

	public void setHubStfEditedDate(Date hubStfEditedDate) {
		this.hubStfEditedDate = hubStfEditedDate;
	}

	public String getHubGatePassEditedBy() {
		return hubGatePassEditedBy;
	}

	public void setHubGatePassEditedBy(String hubGatePassEditedBy) {
		this.hubGatePassEditedBy = hubGatePassEditedBy;
	}

	public Date getHubGatePassEditedDate() {
		return hubGatePassEditedDate;
	}

	public void setHubGatePassEditedDate(Date hubGatePassEditedDate) {
		this.hubGatePassEditedDate = hubGatePassEditedDate;
	}

	public String getHubPortGateInEditedBy() {
		return hubPortGateInEditedBy;
	}

	public void setHubPortGateInEditedBy(String hubPortGateInEditedBy) {
		this.hubPortGateInEditedBy = hubPortGateInEditedBy;
	}

	public Date getHubPortGateInEditedDate() {
		return hubPortGateInEditedDate;
	}

	public void setHubPortGateInEditedDate(Date hubPortGateInEditedDate) {
		this.hubPortGateInEditedDate = hubPortGateInEditedDate;
	}

	public String getBttEditedBy() {
		return bttEditedBy;
	}

	public void setBttEditedBy(String bttEditedBy) {
		this.bttEditedBy = bttEditedBy;
	}

	public Date getBttEditedDate() {
		return bttEditedDate;
	}

	public void setBttEditedDate(Date bttEditedDate) {
		this.bttEditedDate = bttEditedDate;
	}

	public String getMovementEditedBy() {
		return movementEditedBy;
	}

	public void setMovementEditedBy(String movementEditedBy) {
		this.movementEditedBy = movementEditedBy;
	}

	public Date getMovementEditedDate() {
		return movementEditedDate;
	}

	public void setMovementEditedDate(Date movementEditedDate) {
		this.movementEditedDate = movementEditedDate;
	}

	public String getStuffReqEditedBy() {
		return stuffReqEditedBy;
	}

	public void setStuffReqEditedBy(String stuffReqEditedBy) {
		this.stuffReqEditedBy = stuffReqEditedBy;
	}

	public Date getStuffReqEditedDate() {
		return stuffReqEditedDate;
	}

	public void setStuffReqEditedDate(Date stuffReqEditedDate) {
		this.stuffReqEditedDate = stuffReqEditedDate;
	}

	public String getStuffTallyEditedBy() {
		return stuffTallyEditedBy;
	}

	public void setStuffTallyEditedBy(String stuffTallyEditedBy) {
		this.stuffTallyEditedBy = stuffTallyEditedBy;
	}

	public Date getStuffTallyEditedDate() {
		return stuffTallyEditedDate;
	}

	public void setStuffTallyEditedDate(Date stuffTallyEditedDate) {
		this.stuffTallyEditedDate = stuffTallyEditedDate;
	}
    
    
    
}
