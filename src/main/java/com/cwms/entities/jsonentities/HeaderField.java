package com.cwms.entities.jsonentities;

import lombok.Data;

@Data
public class HeaderField {

	private String date;
    private String indicator;
    private String messageID;
    private String receiverID;
    private String reportingEvent;
    private String senderID;
    private int sequenceOrControlNumber;
    private String time;
    private String versionNo;
	public HeaderField() {
		super();
		// TODO Auto-generated constructor stub
	}
	public HeaderField(String date, String indicator, String messageID, String receiverID, String reportingEvent,
			String senderID, int sequenceOrControlNumber, String time, String versionNo) {
		super();
		this.date = date;
		this.indicator = indicator;
		this.messageID = messageID;
		this.receiverID = receiverID;
		this.reportingEvent = reportingEvent;
		this.senderID = senderID;
		this.sequenceOrControlNumber = sequenceOrControlNumber;
		this.time = time;
		this.versionNo = versionNo;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getIndicator() {
		return indicator;
	}
	public void setIndicator(String indicator) {
		this.indicator = indicator;
	}
	public String getMessageID() {
		return messageID;
	}
	public void setMessageID(String messageID) {
		this.messageID = messageID;
	}
	public String getReceiverID() {
		return receiverID;
	}
	public void setReceiverID(String receiverID) {
		this.receiverID = receiverID;
	}
	public String getReportingEvent() {
		return reportingEvent;
	}
	public void setReportingEvent(String reportingEvent) {
		this.reportingEvent = reportingEvent;
	}
	public String getSenderID() {
		return senderID;
	}
	public void setSenderID(String senderID) {
		this.senderID = senderID;
	}
	public int getSequenceOrControlNumber() {
		return sequenceOrControlNumber;
	}
	public void setSequenceOrControlNumber(int sequenceOrControlNumber) {
		this.sequenceOrControlNumber = sequenceOrControlNumber;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getVersionNo() {
		return versionNo;
	}
	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}
    
    
}
