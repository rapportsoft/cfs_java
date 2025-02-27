package com.cwms.entities;

public class PartyUserCreation {

	public String partyId;
	public String partyName;
	public String userId;
	public String password;
	protected PartyUserCreation() {
		super();
		// TODO Auto-generated constructor stub
	}
	protected PartyUserCreation(String partyId, String partyName, String userId, String password) {
		super();
		this.partyId = partyId;
		this.partyName = partyName;
		this.userId = userId;
		this.password = password;
	}
	public String getPartyId() {
		return partyId;
	}
	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}
	public String getPartyName() {
		return partyName;
	}
	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	
}
