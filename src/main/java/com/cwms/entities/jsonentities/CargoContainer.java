package com.cwms.entities.jsonentities;

import java.util.List;

import lombok.Data;

@Data
public class CargoContainer {
	private List<CargoDetails> cargoDetails;
	private String equipmentID;
	private String equipmentLoadStatus;
	private String equipmentQUC;
	private int equipmentQuantity;
	private String equipmentSealNumber;
	private String equipmentSealType;
	private int equipmentSequenceNo;
	private String equipmentSize;
	private String equipmentStatus;
	private String equipmentType;
	private String eventDate;
	private String finalDestinationLocation;
	private String messageType;
	public CargoContainer() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CargoContainer(List<CargoDetails> cargoDetails, String equipmentID, String equipmentLoadStatus,
			String equipmentQUC, int equipmentQuantity, String equipmentSealNumber, String equipmentSealType,
			int equipmentSequenceNo, String equipmentSize, String equipmentStatus, String equipmentType,
			String eventDate, String finalDestinationLocation, String messageType) {
		super();
		this.cargoDetails = cargoDetails;
		this.equipmentID = equipmentID;
		this.equipmentLoadStatus = equipmentLoadStatus;
		this.equipmentQUC = equipmentQUC;
		this.equipmentQuantity = equipmentQuantity;
		this.equipmentSealNumber = equipmentSealNumber;
		this.equipmentSealType = equipmentSealType;
		this.equipmentSequenceNo = equipmentSequenceNo;
		this.equipmentSize = equipmentSize;
		this.equipmentStatus = equipmentStatus;
		this.equipmentType = equipmentType;
		this.eventDate = eventDate;
		this.finalDestinationLocation = finalDestinationLocation;
		this.messageType = messageType;
	}
	public List<CargoDetails> getCargoDetails() {
		return cargoDetails;
	}
	public void setCargoDetails(List<CargoDetails> cargoDetails) {
		this.cargoDetails = cargoDetails;
	}
	public String getEquipmentID() {
		return equipmentID;
	}
	public void setEquipmentID(String equipmentID) {
		this.equipmentID = equipmentID;
	}
	public String getEquipmentLoadStatus() {
		return equipmentLoadStatus;
	}
	public void setEquipmentLoadStatus(String equipmentLoadStatus) {
		this.equipmentLoadStatus = equipmentLoadStatus;
	}
	public String getEquipmentQUC() {
		return equipmentQUC;
	}
	public void setEquipmentQUC(String equipmentQUC) {
		this.equipmentQUC = equipmentQUC;
	}
	public int getEquipmentQuantity() {
		return equipmentQuantity;
	}
	public void setEquipmentQuantity(int equipmentQuantity) {
		this.equipmentQuantity = equipmentQuantity;
	}
	public String getEquipmentSealNumber() {
		return equipmentSealNumber;
	}
	public void setEquipmentSealNumber(String equipmentSealNumber) {
		this.equipmentSealNumber = equipmentSealNumber;
	}
	public String getEquipmentSealType() {
		return equipmentSealType;
	}
	public void setEquipmentSealType(String equipmentSealType) {
		this.equipmentSealType = equipmentSealType;
	}
	public int getEquipmentSequenceNo() {
		return equipmentSequenceNo;
	}
	public void setEquipmentSequenceNo(int equipmentSequenceNo) {
		this.equipmentSequenceNo = equipmentSequenceNo;
	}
	public String getEquipmentSize() {
		return equipmentSize;
	}
	public void setEquipmentSize(String equipmentSize) {
		this.equipmentSize = equipmentSize;
	}
	public String getEquipmentStatus() {
		return equipmentStatus;
	}
	public void setEquipmentStatus(String equipmentStatus) {
		this.equipmentStatus = equipmentStatus;
	}
	public String getEquipmentType() {
		return equipmentType;
	}
	public void setEquipmentType(String equipmentType) {
		this.equipmentType = equipmentType;
	}
	public String getEventDate() {
		return eventDate;
	}
	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}
	public String getFinalDestinationLocation() {
		return finalDestinationLocation;
	}
	public void setFinalDestinationLocation(String finalDestinationLocation) {
		this.finalDestinationLocation = finalDestinationLocation;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	
	
	
}
