package com.cwms.exportAuditTrail;

import java.math.BigDecimal;
import java.util.Date;

public class StuffTally {
	
    	private String sbNo;
	    private String sbTransId;
	    private String newContainerNo;
	    private String newContainerSize;
	    private String newContainerType;	    
	    private Date newStuffDate;
	    private String auditremarks;
	    private String oldvoyageNo;
	    private String oldvcnNo;
	    private String oldVesselId;
	    private String oldVesselName;   
	    
	    
	    private String oldRotataionNo;
	    private String oldPod;
	    private String oldPodDesc;
	    
	    private String oldFinalPod;
	    private String oldFinalPodDesc;
	    
	    private String oldPol;
	    private String oldAgentSealNo;
	    private String oldCustomSealNo;
	    private BigDecimal oldTareWeight;
	    private Date oldMovementRqDate;
	    private BigDecimal oldStuffQty;
	    private BigDecimal oldStuffQtyWeight;    
	    
	    private String oldContainerNo;
	    private String oldContainerSize;
	    private String oldContainerType;	    
	    private Date oldStuffDate;
	    
	    private String newvoyageNo;
	    private String newvcnNo;
	    private String newVesselId;
	    private String newVesselName;
	    
	    private String newRotataionNo;
	    private String newPod;
	    private String newPodDesc;
	    
	    private String newFinalPod;
	    private String newFinalPodDesc;
	    
	    private String newPol;
	    private String newPolDesc;
	    
	    
	    private String newAgentSealNo;
	    private String newCustomSealNo;
	    private BigDecimal newTareWeight;
	    private Date newMovementRqDate;
	    private BigDecimal newStuffQty;
	    private BigDecimal newStuffQtyWeight;  
	    
	    
	    private BigDecimal sbStuffedQty;
	    private BigDecimal sbGrossWeight;  
	    
	    private String profitCentreId;

	    private String gateInId;
		private String gateOutId;
		private String gatePassId;
		private String stuffReqId;
		private String stuffTallyId;
		private String movementId;
	    

		public BigDecimal getSbStuffedQty() {
			return sbStuffedQty;
		}

		public void setSbStuffedQty(BigDecimal sbStuffedQty) {
			this.sbStuffedQty = sbStuffedQty;
		}

		public BigDecimal getSbGrossWeight() {
			return sbGrossWeight;
		}

		public void setSbGrossWeight(BigDecimal sbGrossWeight) {
			this.sbGrossWeight = sbGrossWeight;
		}

		public String getGateInId() {
			return gateInId;
		}

		public void setGateInId(String gateInId) {
			this.gateInId = gateInId;
		}

		public String getGateOutId() {
			return gateOutId;
		}

		public void setGateOutId(String gateOutId) {
			this.gateOutId = gateOutId;
		}

		public String getGatePassId() {
			return gatePassId;
		}

		public void setGatePassId(String gatePassId) {
			this.gatePassId = gatePassId;
		}

		public String getStuffReqId() {
			return stuffReqId;
		}

		public void setStuffReqId(String stuffReqId) {
			this.stuffReqId = stuffReqId;
		}

		public String getStuffTallyId() {
			return stuffTallyId;
		}

		public void setStuffTallyId(String stuffTallyId) {
			this.stuffTallyId = stuffTallyId;
		}

		public String getMovementId() {
			return movementId;
		}

		public void setMovementId(String movementId) {
			this.movementId = movementId;
		}

		public String getAuditremarks() {
			return auditremarks;
		}

		public void setAuditremarks(String auditremarks) {
			this.auditremarks = auditremarks;
		}

		public String getNewPolDesc() {
			return newPolDesc;
		}

		public void setNewPolDesc(String newPolDesc) {
			this.newPolDesc = newPolDesc;
		}

		public StuffTally() {
			super();
			// TODO Auto-generated constructor stub
		}

		public StuffTally(String sbNo, String sbTransId, String newContainerNo, String newContainerSize,
				String newContainerType, Date newStuffDate, String oldvoyageNo, String oldvcnNo, String oldVesselId,
				String oldVesselName, String oldRotataionNo, String oldPod, String oldPodDesc, String oldFinalPod,
				String oldFinalPodDesc, String oldPol, String oldAgentSealNo, String oldCustomSealNo,
				BigDecimal oldTareWeight, Date oldMovementRqDate, BigDecimal stuffQty, BigDecimal stuffQtyWeight,
				String oldContainerNo, String oldContainerSize, String oldContainerType, Date oldStuffDate,
				String newvoyageNo, String newvcnNo, String newVesselId, String newVesselName, String newRotataionNo,
				String newPod, String newPodDesc, String newFinalPod, String newFinalPodDesc, String newPol,
				String newAgentSealNo, String newCustomSealNo, BigDecimal newTareWeight, Date newMovementRqDate,
				BigDecimal newStuffQty, BigDecimal newStuffQtyWeight, String profitCentreId) {
			super();
			this.sbNo = sbNo;
			this.sbTransId = sbTransId;
			this.newContainerNo = newContainerNo;
			this.newContainerSize = newContainerSize;
			this.newContainerType = newContainerType;
			this.newStuffDate = newStuffDate;
			this.oldvoyageNo = oldvoyageNo;
			this.oldvcnNo = oldvcnNo;
			this.oldVesselId = oldVesselId;
			this.oldVesselName = oldVesselName;
			this.oldRotataionNo = oldRotataionNo;
			this.oldPod = oldPod;
			this.oldPodDesc = oldPodDesc;
			this.oldFinalPod = oldFinalPod;
			this.oldFinalPodDesc = oldFinalPodDesc;
			this.oldPol = oldPol;
			this.oldAgentSealNo = oldAgentSealNo;
			this.oldCustomSealNo = oldCustomSealNo;
			this.oldTareWeight = oldTareWeight;
			this.oldMovementRqDate = oldMovementRqDate;
			this.oldStuffQty = stuffQty;
			this.oldStuffQtyWeight = stuffQtyWeight;
			this.oldContainerNo = oldContainerNo;
			this.oldContainerSize = oldContainerSize;
			this.oldContainerType = oldContainerType;
			this.oldStuffDate = oldStuffDate;
			this.newvoyageNo = newvoyageNo;
			this.newvcnNo = newvcnNo;
			this.newVesselId = newVesselId;
			this.newVesselName = newVesselName;
			this.newRotataionNo = newRotataionNo;
			this.newPod = newPod;
			this.newPodDesc = newPodDesc;
			this.newFinalPod = newFinalPod;
			this.newFinalPodDesc = newFinalPodDesc;
			this.newPol = newPol;
			this.newAgentSealNo = newAgentSealNo;
			this.newCustomSealNo = newCustomSealNo;
			this.newTareWeight = newTareWeight;
			this.newMovementRqDate = newMovementRqDate;
			this.newStuffQty = newStuffQty;
			this.newStuffQtyWeight = newStuffQtyWeight;
			this.profitCentreId = profitCentreId;
		}

		public String getSbNo() {
			return sbNo;
		}

		public void setSbNo(String sbNo) {
			this.sbNo = sbNo;
		}

		public String getSbTransId() {
			return sbTransId;
		}

		public void setSbTransId(String sbTransId) {
			this.sbTransId = sbTransId;
		}

		public String getNewContainerNo() {
			return newContainerNo;
		}

		public void setNewContainerNo(String newContainerNo) {
			this.newContainerNo = newContainerNo;
		}

		public String getNewContainerSize() {
			return newContainerSize;
		}

		public void setNewContainerSize(String newContainerSize) {
			this.newContainerSize = newContainerSize;
		}

		public String getNewContainerType() {
			return newContainerType;
		}

		public void setNewContainerType(String newContainerType) {
			this.newContainerType = newContainerType;
		}

		public Date getNewStuffDate() {
			return newStuffDate;
		}

		public void setNewStuffDate(Date newStuffDate) {
			this.newStuffDate = newStuffDate;
		}

		public String getOldvoyageNo() {
			return oldvoyageNo;
		}

		public void setOldvoyageNo(String oldvoyageNo) {
			this.oldvoyageNo = oldvoyageNo;
		}

		public String getOldvcnNo() {
			return oldvcnNo;
		}

		public void setOldvcnNo(String oldvcnNo) {
			this.oldvcnNo = oldvcnNo;
		}

		public String getOldVesselId() {
			return oldVesselId;
		}

		public void setOldVesselId(String oldVesselId) {
			this.oldVesselId = oldVesselId;
		}

		public String getOldVesselName() {
			return oldVesselName;
		}

		public void setOldVesselName(String oldVesselName) {
			this.oldVesselName = oldVesselName;
		}

		public String getOldRotataionNo() {
			return oldRotataionNo;
		}

		public void setOldRotataionNo(String oldRotataionNo) {
			this.oldRotataionNo = oldRotataionNo;
		}

		public String getOldPod() {
			return oldPod;
		}

		public void setOldPod(String oldPod) {
			this.oldPod = oldPod;
		}

		public String getOldPodDesc() {
			return oldPodDesc;
		}

		public void setOldPodDesc(String oldPodDesc) {
			this.oldPodDesc = oldPodDesc;
		}

		public String getOldFinalPod() {
			return oldFinalPod;
		}

		public void setOldFinalPod(String oldFinalPod) {
			this.oldFinalPod = oldFinalPod;
		}

		public String getOldFinalPodDesc() {
			return oldFinalPodDesc;
		}

		public void setOldFinalPodDesc(String oldFinalPodDesc) {
			this.oldFinalPodDesc = oldFinalPodDesc;
		}

		public String getOldPol() {
			return oldPol;
		}

		public void setOldPol(String oldPol) {
			this.oldPol = oldPol;
		}

		public String getOldAgentSealNo() {
			return oldAgentSealNo;
		}

		public void setOldAgentSealNo(String oldAgentSealNo) {
			this.oldAgentSealNo = oldAgentSealNo;
		}

		public String getOldCustomSealNo() {
			return oldCustomSealNo;
		}

		public void setOldCustomSealNo(String oldCustomSealNo) {
			this.oldCustomSealNo = oldCustomSealNo;
		}

		public BigDecimal getOldTareWeight() {
			return oldTareWeight;
		}

		public void setOldTareWeight(BigDecimal oldTareWeight) {
			this.oldTareWeight = oldTareWeight;
		}

		public Date getOldMovementRqDate() {
			return oldMovementRqDate;
		}

		public void setOldMovementRqDate(Date oldMovementRqDate) {
			this.oldMovementRqDate = oldMovementRqDate;
		}

		public BigDecimal getOldStuffQty() {
			return oldStuffQty;
		}

		public void setStuffQty(BigDecimal stuffQty) {
			this.oldStuffQty = stuffQty;
		}

		public BigDecimal getOldStuffQtyWeight() {
			return oldStuffQtyWeight;
		}

		public void setOldStuffQtyWeight(BigDecimal stuffQtyWeight) {
			this.oldStuffQtyWeight = stuffQtyWeight;
		}

		public String getOldContainerNo() {
			return oldContainerNo;
		}

		public void setOldContainerNo(String oldContainerNo) {
			this.oldContainerNo = oldContainerNo;
		}

		public String getOldContainerSize() {
			return oldContainerSize;
		}

		public void setOldContainerSize(String oldContainerSize) {
			this.oldContainerSize = oldContainerSize;
		}

		public String getOldContainerType() {
			return oldContainerType;
		}

		public void setOldContainerType(String oldContainerType) {
			this.oldContainerType = oldContainerType;
		}

		public Date getOldStuffDate() {
			return oldStuffDate;
		}

		public void setOldStuffDate(Date oldStuffDate) {
			this.oldStuffDate = oldStuffDate;
		}

		public String getNewvoyageNo() {
			return newvoyageNo;
		}

		public void setNewvoyageNo(String newvoyageNo) {
			this.newvoyageNo = newvoyageNo;
		}

		public String getNewvcnNo() {
			return newvcnNo;
		}

		public void setNewvcnNo(String newvcnNo) {
			this.newvcnNo = newvcnNo;
		}

		public String getNewVesselId() {
			return newVesselId;
		}

		public void setNewVesselId(String newVesselId) {
			this.newVesselId = newVesselId;
		}

		public String getNewVesselName() {
			return newVesselName;
		}

		public void setNewVesselName(String newVesselName) {
			this.newVesselName = newVesselName;
		}

		public String getNewRotataionNo() {
			return newRotataionNo;
		}

		public void setNewRotataionNo(String newRotataionNo) {
			this.newRotataionNo = newRotataionNo;
		}

		public String getNewPod() {
			return newPod;
		}

		public void setNewPod(String newPod) {
			this.newPod = newPod;
		}

		public String getNewPodDesc() {
			return newPodDesc;
		}

		public void setNewPodDesc(String newPodDesc) {
			this.newPodDesc = newPodDesc;
		}

		public String getNewFinalPod() {
			return newFinalPod;
		}

		public void setNewFinalPod(String newFinalPod) {
			this.newFinalPod = newFinalPod;
		}

		public String getNewFinalPodDesc() {
			return newFinalPodDesc;
		}

		public void setNewFinalPodDesc(String newFinalPodDesc) {
			this.newFinalPodDesc = newFinalPodDesc;
		}

		public String getNewPol() {
			return newPol;
		}

		public void setNewPol(String newPol) {
			this.newPol = newPol;
		}

		public String getNewAgentSealNo() {
			return newAgentSealNo;
		}

		public void setNewAgentSealNo(String newAgentSealNo) {
			this.newAgentSealNo = newAgentSealNo;
		}

		public String getNewCustomSealNo() {
			return newCustomSealNo;
		}

		public void setNewCustomSealNo(String newCustomSealNo) {
			this.newCustomSealNo = newCustomSealNo;
		}

		public BigDecimal getNewTareWeight() {
			return newTareWeight;
		}

		public void setNewTareWeight(BigDecimal newTareWeight) {
			this.newTareWeight = newTareWeight;
		}

		public Date getNewMovementRqDate() {
			return newMovementRqDate;
		}

		public void setNewMovementRqDate(Date newMovementRqDate) {
			this.newMovementRqDate = newMovementRqDate;
		}

		public BigDecimal getNewStuffQty() {
			return newStuffQty;
		}

		public void setNewStuffQty(BigDecimal newStuffQty) {
			this.newStuffQty = newStuffQty;
		}

		public BigDecimal getNewStuffQtyWeight() {
			return newStuffQtyWeight;
		}

		public void setNewStuffQtyWeight(BigDecimal newStuffQtyWeight) {
			this.newStuffQtyWeight = newStuffQtyWeight;
		}

		public String getProfitCentreId() {
			return profitCentreId;
		}

		public void setProfitCentreId(String profitCentreId) {
			this.profitCentreId = profitCentreId;
		}

		@Override
		public String toString() {
			return "StuffTally [sbNo=" + sbNo + ", sbTransId=" + sbTransId + ", newContainerNo=" + newContainerNo
					+ ", newContainerSize=" + newContainerSize + ", newContainerType=" + newContainerType
					+ ", newStuffDate=" + newStuffDate + ", oldvoyageNo=" + oldvoyageNo + ", oldvcnNo=" + oldvcnNo
					+ ", oldVesselId=" + oldVesselId + ", oldVesselName=" + oldVesselName + ", oldRotataionNo="
					+ oldRotataionNo + ", oldPod=" + oldPod + ", oldPodDesc=" + oldPodDesc + ", oldFinalPod="
					+ oldFinalPod + ", oldFinalPodDesc=" + oldFinalPodDesc + ", oldPol=" + oldPol + ", oldAgentSealNo="
					+ oldAgentSealNo + ", oldCustomSealNo=" + oldCustomSealNo + ", oldTareWeight=" + oldTareWeight
					+ ", oldMovementRqDate=" + oldMovementRqDate + ", stuffQty=" + oldStuffQty + ", stuffQtyWeight="
					+ oldStuffQtyWeight + ", oldContainerNo=" + oldContainerNo + ", oldContainerSize=" + oldContainerSize
					+ ", oldContainerType=" + oldContainerType + ", oldStuffDate=" + oldStuffDate + ", newvoyageNo="
					+ newvoyageNo + ", newvcnNo=" + newvcnNo + ", newVesselId=" + newVesselId + ", newVesselName="
					+ newVesselName + ", newRotataionNo=" + newRotataionNo + ", newPod=" + newPod + ", newPodDesc="
					+ newPodDesc + ", newFinalPod=" + newFinalPod + ", newFinalPodDesc=" + newFinalPodDesc + ", newPol="
					+ newPol + ", newAgentSealNo=" + newAgentSealNo + ", newCustomSealNo=" + newCustomSealNo
					+ ", newTareWeight=" + newTareWeight + ", newMovementRqDate=" + newMovementRqDate + ", newStuffQty="
					+ newStuffQty + ", newStuffQtyWeight=" + newStuffQtyWeight + ", profitCentreId=" + profitCentreId
					+ "]";
		}
		
		
		  public StuffTally(
		            String sbTransId, Date newStuffTallyDate, String newContainerNo,
		            String newContainerSize, String newContainerType, String newvoyageNo, String newvcnNo, 
		            String newVesselId, String newVesselName, String newRotataionNo, String newPod, 
		            String newFinalPod, String newPol, String newAgentSealNo, String newCustomSealNo, 
		            BigDecimal newTareWeight, String movementReqId, Date newMovementRqDate, Date newStuffDate, 
		            Date oldStuffTallyDate, String sbNo, BigDecimal newStuffQty, BigDecimal newStuffQtyWeight, 
		            String profitCentreId, String stuffId, String gateInId, String gatePassNo, 
		            String gateOutId, String oldFinalPod, String stuffTallyId, BigDecimal sbStuffedQty , BigDecimal sbSGrossWeight) {

			  this.sbStuffedQty = sbStuffedQty;
			  this.sbGrossWeight = sbSGrossWeight;
			  this.stuffTallyId = stuffTallyId;
			  this.stuffReqId = stuffId;
			  this.gatePassId = gatePassNo;
			  this.movementId = movementReqId;
			  this.gateOutId = gateOutId;
			  
		        this.sbNo = sbNo;
		        this.sbTransId = sbTransId;
		        this.newContainerNo = newContainerNo;
		        this.newContainerSize = newContainerSize;
		        this.newContainerType = newContainerType;
		        this.newvoyageNo = newvoyageNo;
		        this.newvcnNo = newvcnNo;
		        this.newVesselId = newVesselId;
		        this.newVesselName = newVesselName;
		        this.newRotataionNo = newRotataionNo;
		        this.newPod = newPod;
		        this.newFinalPod = newFinalPod;
		        this.newPol = newPol;
		        this.newAgentSealNo = newAgentSealNo;
		        this.newCustomSealNo = newCustomSealNo;
		        this.newTareWeight = newTareWeight;
		        this.newMovementRqDate = newMovementRqDate;
		        this.newStuffDate = oldStuffTallyDate;
		        this.newStuffQty = newStuffQty;
		        this.newStuffQtyWeight = newStuffQtyWeight;

		        // Assigning old values (same as new but before update)
		        this.oldContainerNo = newContainerNo;
		        this.oldContainerSize = newContainerSize;
		        this.oldContainerType = newContainerType;
		        this.oldvoyageNo = newvoyageNo;
		        this.oldvcnNo = newvcnNo;
		        this.oldVesselId = newVesselId;
		        this.oldVesselName = newVesselName;
		        this.oldRotataionNo = newRotataionNo;
		        this.oldPod = newPod;
		        this.oldFinalPod = oldFinalPod;  // Assigned from query
		        this.oldPol = newPol;
		        this.oldAgentSealNo = newAgentSealNo;
		        this.oldCustomSealNo = newCustomSealNo;
		        this.oldTareWeight = newTareWeight;
		        this.oldMovementRqDate = newMovementRqDate;
		        this.oldStuffDate = oldStuffTallyDate;
		        this.oldStuffQty = newStuffQty;
		        this.oldStuffQtyWeight = newStuffQtyWeight;
		        
		        this.oldPodDesc = newPod;
		        this.oldFinalPodDesc = oldFinalPod;  // Assigned from query
		        this.newPolDesc = newPol;
		        
		        this.newPodDesc = newPod;
		        this.newFinalPodDesc = oldFinalPod;  // Assigned from query

		        this.profitCentreId = profitCentreId;
		        
		        this.auditremarks = "";
		        this.gateInId = gateInId;
		    }
	
}
