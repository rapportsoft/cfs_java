package com.cwms.entities.jsonentities;

import java.util.List;

public class CargoDetails1 {

	private String mcinPcin;
	private List<CargoContainer1> cargoContainer;
	private List<CargoItnry> cargoItnry;
	private int cargoSequenceNo;
	private String documentDate;
	private String documentNo;
	private String documentSite;
	private String documentType;
	private String messageType;
	private String packageType;
	private int quantity;
	private String shipmentLoadStatus;
	public CargoDetails1() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CargoDetails1(String mcinPcin, List<CargoContainer1> cargoContainer, List<CargoItnry> cargoItnry,
			int cargoSequenceNo, String documentDate, String documentNo, String documentSite, String documentType,
			String messageType, String packageType, int quantity, String shipmentLoadStatus) {
		super();
		this.mcinPcin = mcinPcin;
		this.cargoContainer = cargoContainer;
		this.cargoItnry = cargoItnry;
		this.cargoSequenceNo = cargoSequenceNo;
		this.documentDate = documentDate;
		this.documentNo = documentNo;
		this.documentSite = documentSite;
		this.documentType = documentType;
		this.messageType = messageType;
		this.packageType = packageType;
		this.quantity = quantity;
		this.shipmentLoadStatus = shipmentLoadStatus;
	}
	public String getMcinPcin() {
		return mcinPcin;
	}
	public void setMcinPcin(String mcinPcin) {
		this.mcinPcin = mcinPcin;
	}
	public List<CargoContainer1> getCargoContainer() {
		return cargoContainer;
	}
	public void setCargoContainer(List<CargoContainer1> cargoContainer) {
		this.cargoContainer = cargoContainer;
	}
	public List<CargoItnry> getCargoItnry() {
		return cargoItnry;
	}
	public void setCargoItnry(List<CargoItnry> cargoItnry) {
		this.cargoItnry = cargoItnry;
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
	public String getDocumentNo() {
		return documentNo;
	}
	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
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
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public String getPackageType() {
		return packageType;
	}
	public void setPackageType(String packageType) {
		this.packageType = packageType;
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
