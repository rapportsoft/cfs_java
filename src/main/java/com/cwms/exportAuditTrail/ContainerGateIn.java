package com.cwms.exportAuditTrail;

import java.math.BigDecimal;
import java.util.Date;

public class ContainerGateIn {

	private String auditremarks;
	private String sbNo;
	private String sbTranId;
	private String containerNo;
	private String gateInId;
	
	
	private String stuffReqId;
	private String stuffTallyId;
	private String movementReqId;
	private String gateOutId;
	private String gatePassNo;
	
	
	
	
	
	

	private String newGatePassNo;
	private Date newGateInDate;
	private String newContainerNo;
	private String newContainerSize;
	private String newContainerType;
	private String newIsoCode;
	private BigDecimal newTareWeight;
	private String newExporter;
	private String newTruckNo;
	private BigDecimal newGrossWeight;
	private String newAgentSealNo;
	private String newCustomSealNo;
	private String newTransporter;
	private String newDamageDetails;
	private String newMovementType;
	private String newMovementBy;
	private String newCHA;
	private String newShippingLine;
	private String newShippingAgent;
	private String profitCentreId;

	private String oldGatePassNo;
	private Date oldGateInDate;
	private String oldContainerNo;
	private String oldContainerSize;
	private String oldContainerType;
	private String oldIsoCode;
	private BigDecimal oldTareWeight;
	private String oldExporter;
	private String oldTruckNo;
	private BigDecimal oldGrossWeight;
	private String oldAgentSealNo;
	private String oldCustomSealNo;
	private String oldTransporter;
	private String oldDamageDetails;
	private String oldMovementType;
	private String oldMovementBy;
	private String oldCHA;
	private String oldShippingLine;
	private String oldShippingAgent;

	private String exporterName;
	private String shippingAgentName;
	private String shippingLineName;
	private String chaName;
	
	private String newTransporterName;
	private String oldTransporterName;
	
	
	
	

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

	public String getMovementReqId() {
		return movementReqId;
	}

	public void setMovementReqId(String movementReqId) {
		this.movementReqId = movementReqId;
	}

	public String getGateOutId() {
		return gateOutId;
	}

	public void setGateOutId(String gateOutId) {
		this.gateOutId = gateOutId;
	}

	public String getGatePassNo() {
		return gatePassNo;
	}

	public void setGatePassNo(String gatePassNo) {
		this.gatePassNo = gatePassNo;
	}

	public ContainerGateIn() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		return "ContainerGateIn [auditRemarks=" + auditremarks + ", sbNo=" + sbNo + ", sbTranId=" + sbTranId
				+ ", containerNo=" + containerNo + ", oldGateInId="
				+ ", newGatePassNo=" + newGatePassNo + ", newGateInDate=" + newGateInDate + ", newContainerNo="
				+ newContainerNo + ", newContainerSize=" + newContainerSize + ", newContainerType=" + newContainerType
				+ ", newIsoCode=" + newIsoCode + ", newTareWeight=" + newTareWeight + ", newExporter=" + newExporter
				+ ", newTruckNo=" + newTruckNo + ", newGrossWeight=" + newGrossWeight + ", newAgentSealNo="
				+ newAgentSealNo + ", newCustomSealNo=" + newCustomSealNo + ", newTransporter=" + newTransporter
				+ ", newDamageDetails=" + newDamageDetails + ", newMovementType=" + newMovementType + ", newMovementBy="
				+ newMovementBy + ", newCHA=" + newCHA + ", newShippingLine=" + newShippingLine + ", newShippingAgent="
				+ newShippingAgent + ", profitCentreId=" + profitCentreId + ", oldGatePassNo=" + oldGatePassNo
				+ ", oldGateInDate=" + oldGateInDate + ", oldContainerNo=" + oldContainerNo + ", oldContainerSize="
				+ oldContainerSize + ", oldContainerType=" + oldContainerType + ", oldIsoCode=" + oldIsoCode
				+ ", oldTareWeight=" + oldTareWeight + ", oldExporter=" + oldExporter + ", oldTruckNo=" + oldTruckNo
				+ ", oldGrossWeight=" + oldGrossWeight + ", oldAgentSealNo=" + oldAgentSealNo + ", oldCustomSealNo="
				+ oldCustomSealNo + ", oldTransporter=" + oldTransporter + ", oldDamageDetails=" + oldDamageDetails
				+ ", oldMovementType=" + oldMovementType + ", oldMovementBy=" + oldMovementBy + ", oldCHA=" + oldCHA
				+ ", oldShippingLine=" + oldShippingLine + ", oldShippingAgent=" + oldShippingAgent + ", exporterName="
				+ exporterName + ", shippingAgentName=" + shippingAgentName + ", shippingLineName=" + shippingLineName
				+ ", chaName=" + chaName + ", newTransporterName=" + newTransporterName + ", oldTransporterName="
				+ oldTransporterName + "]";
	}

	public String getAuditremarks() {
		return auditremarks;
	}

	public void setAuditremarks(String auditremarks) {
		this.auditremarks = auditremarks;
	}

	public String getSbNo() {
		return sbNo;
	}





	public void setSbNo(String sbNo) {
		this.sbNo = sbNo;
	}





	public String getSbTranId() {
		return sbTranId;
	}





	public void setSbTranId(String sbTranId) {
		this.sbTranId = sbTranId;
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

	public String getNewGatePassNo() {
		return newGatePassNo;
	}





	public void setNewGatePassNo(String newGatePassNo) {
		this.newGatePassNo = newGatePassNo;
	}





	public Date getNewGateInDate() {
		return newGateInDate;
	}





	public void setNewGateInDate(Date newGateInDate) {
		this.newGateInDate = newGateInDate;
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






	public String getNewIsoCode() {
		return newIsoCode;
	}





	public void setNewIsoCode(String newIsoCode) {
		this.newIsoCode = newIsoCode;
	}





	public BigDecimal getNewTareWeight() {
		return newTareWeight;
	}





	public void setNewTareWeight(BigDecimal newTareWeight) {
		this.newTareWeight = newTareWeight;
	}





	public String getNewExporter() {
		return newExporter;
	}





	public void setNewExporter(String newExporter) {
		this.newExporter = newExporter;
	}





	public String getNewTruckNo() {
		return newTruckNo;
	}





	public void setNewTruckNo(String newTruckNo) {
		this.newTruckNo = newTruckNo;
	}





	public BigDecimal getNewGrossWeight() {
		return newGrossWeight;
	}





	public void setNewGrossWeight(BigDecimal newGrossWeight) {
		this.newGrossWeight = newGrossWeight;
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





	public String getNewTransporter() {
		return newTransporter;
	}





	public void setNewTransporter(String newTransporter) {
		this.newTransporter = newTransporter;
	}





	public String getNewDamageDetails() {
		return newDamageDetails;
	}





	public void setNewDamageDetails(String newDamageDetails) {
		this.newDamageDetails = newDamageDetails;
	}





	public String getNewMovementType() {
		return newMovementType;
	}





	public void setNewMovementType(String newMovementType) {
		this.newMovementType = newMovementType;
	}





	public String getNewMovementBy() {
		return newMovementBy;
	}





	public void setNewMovementBy(String newMovementBy) {
		this.newMovementBy = newMovementBy;
	}





	public String getNewCHA() {
		return newCHA;
	}





	public void setNewCHA(String newCHA) {
		this.newCHA = newCHA;
	}





	public String getNewShippingLine() {
		return newShippingLine;
	}





	public void setNewShippingLine(String newShippingLine) {
		this.newShippingLine = newShippingLine;
	}





	public String getNewShippingAgent() {
		return newShippingAgent;
	}





	public void setNewShippingAgent(String newShippingAgent) {
		this.newShippingAgent = newShippingAgent;
	}





	public String getProfitCentreId() {
		return profitCentreId;
	}





	public void setProfitCentreId(String profitCentreId) {
		this.profitCentreId = profitCentreId;
	}





	public String getOldGatePassNo() {
		return oldGatePassNo;
	}





	public void setOldGatePassNo(String oldGatePassNo) {
		this.oldGatePassNo = oldGatePassNo;
	}





	public Date getOldGateInDate() {
		return oldGateInDate;
	}





	public void setOldGateInDate(Date oldGateInDate) {
		this.oldGateInDate = oldGateInDate;
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

	public String getNewContainerType() {
		return newContainerType;
	}

	public void setNewContainerType(String newContainerType) {
		this.newContainerType = newContainerType;
	}

	public String getOldContainerType() {
		return oldContainerType;
	}

	public void setOldContainerType(String oldContainerType) {
		this.oldContainerType = oldContainerType;
	}

	public String getOldIsoCode() {
		return oldIsoCode;
	}





	public void setOldIsoCode(String oldIsoCode) {
		this.oldIsoCode = oldIsoCode;
	}





	public BigDecimal getOldTareWeight() {
		return oldTareWeight;
	}





	public void setOldTareWeight(BigDecimal oldTareWeight) {
		this.oldTareWeight = oldTareWeight;
	}





	public String getOldExporter() {
		return oldExporter;
	}





	public void setOldExporter(String oldExporter) {
		this.oldExporter = oldExporter;
	}





	public String getOldTruckNo() {
		return oldTruckNo;
	}





	public void setOldTruckNo(String oldTruckNo) {
		this.oldTruckNo = oldTruckNo;
	}





	public BigDecimal getOldGrossWeight() {
		return oldGrossWeight;
	}





	public void setOldGrossWeight(BigDecimal oldGrossWeight) {
		this.oldGrossWeight = oldGrossWeight;
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





	public String getOldTransporter() {
		return oldTransporter;
	}





	public void setOldTransporter(String oldTransporter) {
		this.oldTransporter = oldTransporter;
	}





	public String getOldDamageDetails() {
		return oldDamageDetails;
	}





	public void setOldDamageDetails(String oldDamageDetails) {
		this.oldDamageDetails = oldDamageDetails;
	}





	public String getOldMovementType() {
		return oldMovementType;
	}





	public void setOldMovementType(String oldMovementType) {
		this.oldMovementType = oldMovementType;
	}





	public String getOldMovementBy() {
		return oldMovementBy;
	}





	public void setOldMovementBy(String oldMovementBy) {
		this.oldMovementBy = oldMovementBy;
	}





	public String getOldCHA() {
		return oldCHA;
	}





	public void setOldCHA(String oldCHA) {
		this.oldCHA = oldCHA;
	}





	public String getOldShippingLine() {
		return oldShippingLine;
	}





	public void setOldShippingLine(String oldShippingLine) {
		this.oldShippingLine = oldShippingLine;
	}





	public String getOldShippingAgent() {
		return oldShippingAgent;
	}





	public void setOldShippingAgent(String oldShippingAgent) {
		this.oldShippingAgent = oldShippingAgent;
	}





	public String getExporterName() {
		return exporterName;
	}





	public void setExporterName(String exporterName) {
		this.exporterName = exporterName;
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





	public String getNewTransporterName() {
		return newTransporterName;
	}





	public void setNewTransporterName(String newTransporterName) {
		this.newTransporterName = newTransporterName;
	}





	public String getOldTransporterName() {
		return oldTransporterName;
	}





	public void setOldTransporterName(String oldTransporterName) {
		this.oldTransporterName = oldTransporterName;
	}





	public ContainerGateIn(String auditRemarks, String sbNo, String sbTranId, String containerNo, String oldGateInId,
			String newGateInId, String newGatePassNo, Date newGateInDate, String newContainerNo,
			String newContainerSize, String newCargoType, String newIsoCode, BigDecimal newTareWeight,
			String newExporter, String newTruckNo, BigDecimal newGrossWeight, String newAgentSealNo,
			String newCustomSealNo, String newTransporter, String newDamageDetails, String newMovementType,
			String newMovementBy, String newCHA, String newShippingLine, String newShippingAgent, String profitCentreId,
			String oldGatePassNo, Date oldGateInDate, String oldContainerNo, String oldContainerSize,
			String oldCargoType, String oldIsoCode, BigDecimal oldTareWeight, String oldExporter, String oldTruckNo,
			BigDecimal oldGrossWeight, String oldAgentSealNo, String oldCustomSealNo, String oldTransporter,
			String oldDamageDetails, String oldMovementType, String oldMovementBy, String oldCHA,
			String oldShippingLine, String oldShippingAgent, String exporterName, String shippingAgentName,
			String shippingLineName, String chaName, String newTransporterName, String oldTransporterName) {
		super();
		this.auditremarks = auditRemarks;
		this.sbNo = sbNo;
		this.sbTranId = sbTranId;
		this.containerNo = containerNo;
		this.gateInId = oldGateInId;
		this.newGatePassNo = newGatePassNo;
		this.newGateInDate = newGateInDate;
		this.newContainerNo = newContainerNo;
		this.newContainerSize = newContainerSize;
		this.newContainerType = newCargoType;
		this.newIsoCode = newIsoCode;
		this.newTareWeight = newTareWeight;
		this.newExporter = newExporter;
		this.newTruckNo = newTruckNo;
		this.newGrossWeight = newGrossWeight;
		this.newAgentSealNo = newAgentSealNo;
		this.newCustomSealNo = newCustomSealNo;
		this.newTransporter = newTransporter;
		this.newDamageDetails = newDamageDetails;
		this.newMovementType = newMovementType;
		this.newMovementBy = newMovementBy;
		this.newCHA = newCHA;
		this.newShippingLine = newShippingLine;
		this.newShippingAgent = newShippingAgent;
		this.profitCentreId = profitCentreId;
		this.oldGatePassNo = oldGatePassNo;
		this.oldGateInDate = oldGateInDate;
		this.oldContainerNo = oldContainerNo;
		this.oldContainerSize = oldContainerSize;
		this.oldContainerType = oldCargoType;
		this.oldIsoCode = oldIsoCode;
		this.oldTareWeight = oldTareWeight;
		this.oldExporter = oldExporter;
		this.oldTruckNo = oldTruckNo;
		this.oldGrossWeight = oldGrossWeight;
		this.oldAgentSealNo = oldAgentSealNo;
		this.oldCustomSealNo = oldCustomSealNo;
		this.oldTransporter = oldTransporter;
		this.oldDamageDetails = oldDamageDetails;
		this.oldMovementType = oldMovementType;
		this.oldMovementBy = oldMovementBy;
		this.oldCHA = oldCHA;
		this.oldShippingLine = oldShippingLine;
		this.oldShippingAgent = oldShippingAgent;
		this.exporterName = exporterName;
		this.shippingAgentName = shippingAgentName;
		this.shippingLineName = shippingLineName;
		this.chaName = chaName;
		this.newTransporterName = newTransporterName;
		this.oldTransporterName = oldTransporterName;
	}





	public ContainerGateIn(String gateInId, Date inGateInDate, String containerNo, String containerSize,
			String containerType, String isoCode, BigDecimal tareWeight, String vehicleNo, BigDecimal grossWeight,
			String containerSealNo, String customsSealNo, String transporter, String transporterName,
			String damageDetails, String sl, String slPartyName, String sa, String saPartyName,
			String createdBy, String profitCentreId, String stuffReqId, String stuffTallyId, String movementReqId,
			String gateOutId, String gatePassNo, String newExporter, String cha, String chaName) {
		this.stuffReqId = stuffReqId;		
		this.stuffTallyId = stuffTallyId;
		this.movementReqId = movementReqId;		
		this.gateOutId = gateOutId;
		this.gatePassNo = gatePassNo;
		
		this.gateInId = gateInId;		
		this.oldGatePassNo = gateInId;
		this.newGatePassNo = gateInId;
		
		this.oldGateInDate = inGateInDate;
		this.newGateInDate = inGateInDate;
		this.containerNo = containerNo;
		this.newContainerNo = containerNo;
		this.oldContainerNo = containerNo;
		this.oldContainerSize = containerSize;
		this.newContainerSize = containerSize;
		this.newContainerType = containerType;
		this.oldContainerType = containerType;
		this.newIsoCode = isoCode;
		this.oldIsoCode = isoCode;
		this.newTareWeight = tareWeight;
		this.oldTareWeight = tareWeight;
		this.newTruckNo = vehicleNo;
		this.oldTruckNo = vehicleNo;
		this.oldGrossWeight = grossWeight;
		this.newGrossWeight = grossWeight;
		this.oldAgentSealNo = containerSealNo;
		this.newAgentSealNo = containerSealNo;
		this.oldCustomSealNo = customsSealNo;
		this.newCustomSealNo = customsSealNo;
		this.newTransporter = (transporter == null || transporter.isEmpty()) ? transporterName : transporter;
		this.oldTransporter = (transporter == null || transporter.isEmpty()) ? transporterName : transporter;	
		this.oldTransporterName = transporterName;
		this.newTransporterName = transporterName;
		this.oldDamageDetails = damageDetails;
		this.newDamageDetails = damageDetails;
		this.oldMovementBy = createdBy;
		this.newMovementBy = createdBy;
		this.newShippingLine = sl;
		this.oldShippingLine = sl;
		this.shippingLineName = slPartyName;
		this.newShippingAgent = sa;
		this.oldShippingAgent = sa;
		this.shippingAgentName = saPartyName;
		this.auditremarks = "";
		this.newCHA = cha;
		this.oldCHA = cha;
		this.chaName = chaName;
	
		this.profitCentreId = profitCentreId;
		
this.newExporter = newExporter;
this.oldExporter = newExporter;
	}

}
