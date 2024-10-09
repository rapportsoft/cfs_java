package com.cwms.entities;

import java.util.Date;

public class HoldDto {
    public String igmNo;
    public String igmLineNo;
    public String igmTransId;
    public String containerNo;
    public String holdId;
    public String gateInId;
    public String holdingAgency;
    public Date holdDate;
    public String holdRemarks;
    public String holdBy;
	public HoldDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public HoldDto(String igmNo, String igmLineNo, String igmTransId, String containerNo, String holdId,
			String gateInId, String holdingAgency, Date holdDate, String holdRemarks, String holdBy) {
		super();
		this.igmNo = igmNo;
		this.igmLineNo = igmLineNo;
		this.igmTransId = igmTransId;
		this.containerNo = containerNo;
		this.holdId = holdId;
		this.gateInId = gateInId;
		this.holdingAgency = holdingAgency;
		this.holdDate = holdDate;
		this.holdRemarks = holdRemarks;
		this.holdBy = holdBy;
	}

	public String getIgmNo() {
		return igmNo;
	}
	public void setIgmNo(String igmNo) {
		this.igmNo = igmNo;
	}
	public String getIgmLineNo() {
		return igmLineNo;
	}
	public void setIgmLineNo(String igmLineNo) {
		this.igmLineNo = igmLineNo;
	}
	public String getIgmTransId() {
		return igmTransId;
	}
	public void setIgmTransId(String igmTransId) {
		this.igmTransId = igmTransId;
	}
	public String getContainerNo() {
		return containerNo;
	}
	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
	}
	
	public String getHoldId() {
		return holdId;
	}
	public void setHoldId(String holdId) {
		this.holdId = holdId;
	}
	public String getHoldingAgency() {
		return holdingAgency;
	}
	public void setHoldingAgency(String holdingAgency) {
		this.holdingAgency = holdingAgency;
	}
	public Date getHoldDate() {
		return holdDate;
	}
	public void setHoldDate(Date holdDate) {
		this.holdDate = holdDate;
	}
	public String getHoldRemarks() {
		return holdRemarks;
	}
	public void setHoldRemarks(String holdRemarks) {
		this.holdRemarks = holdRemarks;
	}
	
	public String getGateInId() {
		return gateInId;
	}

	public void setGateInId(String gateInId) {
		this.gateInId = gateInId;
	}

	public String getHoldBy() {
		return holdBy;
	}
	public void setHoldBy(String holdBy) {
		this.holdBy = holdBy;
	}
    
    
    
}
