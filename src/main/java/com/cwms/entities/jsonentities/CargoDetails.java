package com.cwms.entities.jsonentities;

import lombok.Data;

@Data
public class CargoDetails {
	private int cargoSequenceNo;
	private String documentDate;
	private String documentNumber;
	private String documentSite;
	private String documentType;
	private String mcinPcin;
	private String messageType;
	private String packUQC;
	private String packageType;
	private int packetsFrom;
	private int packetsTo;
	private int quantity;
	private String shipmentLoadStatus;
	public CargoDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CargoDetails(int cargoSequenceNo, String documentDate, String documentNumber, String documentSite,
			String documentType, String mcinPcin, String messageType, String packUQC, String packageType,
			int packetsFrom, int packetsTo, int quantity, String shipmentLoadStatus) {
		super();
		this.cargoSequenceNo = cargoSequenceNo;
		this.documentDate = documentDate;
		this.documentNumber = documentNumber;
		this.documentSite = documentSite;
		this.documentType = documentType;
		this.mcinPcin = mcinPcin;
		this.messageType = messageType;
		this.packUQC = packUQC;
		this.packageType = packageType;
		this.packetsFrom = packetsFrom;
		this.packetsTo = packetsTo;
		this.quantity = quantity;
		this.shipmentLoadStatus = shipmentLoadStatus;
	}
	public int getCargoSequenceNo() {
		return cargoSequenceNo;
	}
	public void setCargoSequenceNo(int cargoSequenceNo) {
		this.cargoSequenceNo = cargoSequenceNo;
	}
	public String getDocumentDate() {
		return documentDate;
	}
	public void setDocumentDate(String documentDate) {
		this.documentDate = documentDate;
	}
	public String getDocumentNumber() {
		return documentNumber;
	}
	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}
	public String getDocumentSite() {
		return documentSite;
	}
	public void setDocumentSite(String documentSite) {
		this.documentSite = documentSite;
	}
	public String getDocumentType() {
		return documentType;
	}
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	public String getMcinPcin() {
		return mcinPcin;
	}
	public void setMcinPcin(String mcinPcin) {
		this.mcinPcin = mcinPcin;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public String getPackUQC() {
		return packUQC;
	}
	public void setPackUQC(String packUQC) {
		this.packUQC = packUQC;
	}
	public String getPackageType() {
		return packageType;
	}
	public void setPackageType(String packageType) {
		this.packageType = packageType;
	}
	public int getPacketsFrom() {
		return packetsFrom;
	}
	public void setPacketsFrom(int packetsFrom) {
		this.packetsFrom = packetsFrom;
	}
	public int getPacketsTo() {
		return packetsTo;
	}
	public void setPacketsTo(int packetsTo) {
		this.packetsTo = packetsTo;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getShipmentLoadStatus() {
		return shipmentLoadStatus;
	}
	public void setShipmentLoadStatus(String shipmentLoadStatus) {
		this.shipmentLoadStatus = shipmentLoadStatus;
	}
	
	
	
}
