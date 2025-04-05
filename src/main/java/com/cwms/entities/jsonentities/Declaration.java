package com.cwms.entities.jsonentities;

import lombok.Data;

@Data
public class Declaration {

	private String jobDate;
	private int jobNo;
	private String messageType;
	private String portOfReporting;
	private String reportingEvent;
	public Declaration(String jobDate, int jobNo, String messageType, String portOfReporting, String reportingEvent) {
		super();
		this.jobDate = jobDate;
		this.jobNo = jobNo;
		this.messageType = messageType;
		this.portOfReporting = portOfReporting;
		this.reportingEvent = reportingEvent;
	}
	public Declaration() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getJobDate() {
		return jobDate;
	}
	public void setJobDate(String jobDate) {
		this.jobDate = jobDate;
	}
	public int getJobNo() {
		return jobNo;
	}
	public void setJobNo(int jobNo) {
		this.jobNo = jobNo;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public String getPortOfReporting() {
		return portOfReporting;
	}
	public void setPortOfReporting(String portOfReporting) {
		this.portOfReporting = portOfReporting;
	}
	public String getReportingEvent() {
		return reportingEvent;
	}
	public void setReportingEvent(String reportingEvent) {
		this.reportingEvent = reportingEvent;
	}

    
}
