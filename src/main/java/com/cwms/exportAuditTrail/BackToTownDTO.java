package com.cwms.exportAuditTrail;

import java.math.BigDecimal;
import java.util.Date;

public class BackToTownDTO {
	
	private String sbNo;
    private String sbTransId;
    private String auditremarks;   
    private String backToTownTransId;
    
    private BigDecimal actualNoOfPackages;
    private BigDecimal grossWeight;
    
    
    private BigDecimal oldBackToTownPackages;
    private Date oldBackToTownTransDate;    
    private BigDecimal oldBackToTownWeight;
    
    
    private BigDecimal newBackToTownPackages;
    private Date newBackToTownTransDate;    
    private BigDecimal newBackToTownWeight;
    
    
    
    
    
	public String getSbNo() {
		return sbNo;
	}
	public String getSbTransId() {
		return sbTransId;
	}
	public String getAuditremarks() {
		return auditremarks;
	}
	public String getBackToTownTransId() {
		return backToTownTransId;
	}
	public BigDecimal getActualNoOfPackages() {
		return actualNoOfPackages;
	}
	public BigDecimal getGrossWeight() {
		return grossWeight;
	}
	public BigDecimal getOldBackToTownPackages() {
		return oldBackToTownPackages;
	}
	public Date getOldBackToTownTransDate() {
		return oldBackToTownTransDate;
	}
	public BigDecimal getOldBackToTownWeight() {
		return oldBackToTownWeight;
	}
	public BigDecimal getNewBackToTownPackages() {
		return newBackToTownPackages;
	}
	public Date getNewBackToTownTransDate() {
		return newBackToTownTransDate;
	}
	public BigDecimal getNewBackToTownWeight() {
		return newBackToTownWeight;
	}
	public BackToTownDTO(String sbNo, String sbTransId, String auditremarks, String backToTownTransId,
			BigDecimal actualNoOfPackages, BigDecimal grossWeight, BigDecimal oldBackToTownPackages,
			Date oldBackToTownTransDate, BigDecimal oldBackToTownWeight, BigDecimal newBackToTownPackages,
			Date newBackToTownTransDate, BigDecimal newBackToTownWeight) {
		super();
		this.sbNo = sbNo;
		this.sbTransId = sbTransId;
		this.auditremarks = auditremarks;
		this.backToTownTransId = backToTownTransId;
		this.actualNoOfPackages = actualNoOfPackages;
		this.grossWeight = grossWeight;
		this.oldBackToTownPackages = oldBackToTownPackages;
		this.oldBackToTownTransDate = oldBackToTownTransDate;
		this.oldBackToTownWeight = oldBackToTownWeight;
		this.newBackToTownPackages = newBackToTownPackages;
		this.newBackToTownTransDate = newBackToTownTransDate;
		this.newBackToTownWeight = newBackToTownWeight;
	}
	public BackToTownDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public void setSbNo(String sbNo) {
		this.sbNo = sbNo;
	}
	public void setSbTransId(String sbTransId) {
		this.sbTransId = sbTransId;
	}
	public void setAuditremarks(String auditremarks) {
		this.auditremarks = auditremarks;
	}
	public void setBackToTownTransId(String backToTownTransId) {
		this.backToTownTransId = backToTownTransId;
	}
	public void setActualNoOfPackages(BigDecimal actualNoOfPackages) {
		this.actualNoOfPackages = actualNoOfPackages;
	}
	public void setGrossWeight(BigDecimal grossWeight) {
		this.grossWeight = grossWeight;
	}
	public void setOldBackToTownPackages(BigDecimal oldBackToTownPackages) {
		this.oldBackToTownPackages = oldBackToTownPackages;
	}
	public void setOldBackToTownTransDate(Date oldBackToTownTransDate) {
		this.oldBackToTownTransDate = oldBackToTownTransDate;
	}
	public void setOldBackToTownWeight(BigDecimal oldBackToTownWeight) {
		this.oldBackToTownWeight = oldBackToTownWeight;
	}
	public void setNewBackToTownPackages(BigDecimal newBackToTownPackages) {
		this.newBackToTownPackages = newBackToTownPackages;
	}
	public void setNewBackToTownTransDate(Date newBackToTownTransDate) {
		this.newBackToTownTransDate = newBackToTownTransDate;
	}
	public void setNewBackToTownWeight(BigDecimal newBackToTownWeight) {
		this.newBackToTownWeight = newBackToTownWeight;
	}
	@Override
	public String toString() {
		return "BackToTownDTO [sbNo=" + sbNo + ", sbTransId=" + sbTransId + ", auditremarks=" + auditremarks
				+ ", backToTownTransId=" + backToTownTransId + ", actualNoOfPackages=" + actualNoOfPackages
				+ ", grossWeight=" + grossWeight + ", oldBackToTownPackages=" + oldBackToTownPackages
				+ ", oldBackToTownTransDate=" + oldBackToTownTransDate + ", oldBackToTownWeight=" + oldBackToTownWeight
				+ ", newBackToTownPackages=" + newBackToTownPackages + ", newBackToTownTransDate="
				+ newBackToTownTransDate + ", newBackToTownWeight=" + newBackToTownWeight + "]";
	}
        
	
			public BackToTownDTO(String backToTownTransId, Date backToTownTransDate, String sbTransId, String sbNo,
					BigDecimal backToTownPackages, BigDecimal backToTownWeight, BigDecimal actualNoOfPackages,
					BigDecimal grossWeight) {
				super();
				this.sbNo = sbNo;
				this.sbTransId = sbTransId;
				this.auditremarks = "";
				this.backToTownTransId = backToTownTransId;
				this.actualNoOfPackages = actualNoOfPackages;
				this.grossWeight = grossWeight;
				this.oldBackToTownPackages = backToTownPackages;
				this.oldBackToTownTransDate = backToTownTransDate;
				this.oldBackToTownWeight = backToTownWeight;
				this.newBackToTownPackages = backToTownPackages;
				this.newBackToTownTransDate = backToTownTransDate;
				this.newBackToTownWeight = backToTownWeight;
			}
}
