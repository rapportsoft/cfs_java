package com.cwms.entities.jsonentities;

import lombok.Data;

@Data
public class Location {
    private String authorisedPersonPAN;
    private String reportingLocationCode;
    private String reportingLocationName;
    private String reportingPartyCode;
    private String reportingPartyType;
	public Location() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Location(String authorisedPersonPAN, String reportingLocationCode, String reportingLocationName,
			String reportingPartyCode, String reportingPartyType) {
		super();
		this.authorisedPersonPAN = authorisedPersonPAN;
		this.reportingLocationCode = reportingLocationCode;
		this.reportingLocationName = reportingLocationName;
		this.reportingPartyCode = reportingPartyCode;
		this.reportingPartyType = reportingPartyType;
	}
	public String getAuthorisedPersonPAN() {
		return authorisedPersonPAN;
	}
	public void setAuthorisedPersonPAN(String authorisedPersonPAN) {
		this.authorisedPersonPAN = authorisedPersonPAN;
	}
	public String getReportingLocationCode() {
		return reportingLocationCode;
	}
	public void setReportingLocationCode(String reportingLocationCode) {
		this.reportingLocationCode = reportingLocationCode;
	}
	public String getReportingLocationName() {
		return reportingLocationName;
	}
	public void setReportingLocationName(String reportingLocationName) {
		this.reportingLocationName = reportingLocationName;
	}
	public String getReportingPartyCode() {
		return reportingPartyCode;
	}
	public void setReportingPartyCode(String reportingPartyCode) {
		this.reportingPartyCode = reportingPartyCode;
	}
	public String getReportingPartyType() {
		return reportingPartyType;
	}
	public void setReportingPartyType(String reportingPartyType) {
		this.reportingPartyType = reportingPartyType;
	}
    
    
}
