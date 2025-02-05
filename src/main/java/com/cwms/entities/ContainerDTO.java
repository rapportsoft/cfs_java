package com.cwms.entities;

import java.math.BigDecimal;
import java.util.Date;

public class ContainerDTO {
    public ContainerDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	private String containerNo;
    private String containerSize;
    private String containerType;
    private Date gateInDate;
    private int noOfPackages;
    private BigDecimal containerWeight;
    private String gateInId;

    public ContainerDTO(String containerNo, String containerSize, String containerType,
                        Date gateInDate, int noOfPackages, BigDecimal containerWeight, String gateInId) {
        this.containerNo = containerNo;
        this.containerSize = containerSize;
        this.containerType = containerType;
        this.gateInDate = gateInDate;
        this.noOfPackages = noOfPackages;
        this.containerWeight = containerWeight;
        this.gateInId = gateInId;
    }

	public String getContainerNo() {
		return containerNo;
	}

	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
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

	public Date getGateInDate() {
		return gateInDate;
	}

	public void setGateInDate(Date gateInDate) {
		this.gateInDate = gateInDate;
	}

	public int getNoOfPackages() {
		return noOfPackages;
	}

	public void setNoOfPackages(int noOfPackages) {
		this.noOfPackages = noOfPackages;
	}

	public BigDecimal getContainerWeight() {
		return containerWeight;
	}

	public void setContainerWeight(BigDecimal containerWeight) {
		this.containerWeight = containerWeight;
	}

	public String getGateInId() {
		return gateInId;
	}

	public void setGateInId(String gateInId) {
		this.gateInId = gateInId;
	}

	@Override
	public String toString() {
		return "ContainerDTO [containerNo=" + containerNo + ", containerSize=" + containerSize + ", containerType="
				+ containerType + ", gateInDate=" + gateInDate + ", noOfPackages=" + noOfPackages + ", containerWeight="
				+ containerWeight + ", gateInId=" + gateInId + "]";
	}

    // Getters and setters
}