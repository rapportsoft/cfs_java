package com.cwms.entities;

public class PartyModal {

	public Party party;
	public AddressDetails address;
	
	
	
	
	public Party getParty() {
		return party;
	}




	public void setParty(Party party) {
		this.party = party;
	}




	public AddressDetails getAddress() {
		return address;
	}




	public void setAddress(AddressDetails address) {
		this.address = address;
	}




	public PartyModal(Party party, AddressDetails address) {
		super();
		this.party = party;
		this.address = address;
	}




	public PartyModal() {
		super();
		// TODO Auto-generated constructor stub
	}




	public static class AddressDetails{
		public int srNo;
		public String address1;
		public String address2;
		public String address3;
		public String city;
		public String pin;
		public String state;
		public String gstNo;
		public String defaultChk;
		public String customerType;
		
		
		
		public AddressDetails(int srNo, String address1, String address2, String address3, String city, String pin,
				String state, String gstNo, String defaultChk, String customerType) {
			super();
			this.srNo = srNo;
			this.address1 = address1;
			this.address2 = address2;
			this.address3 = address3;
			this.city = city;
			this.pin = pin;
			this.state = state;
			this.gstNo = gstNo;
			this.defaultChk = defaultChk;
			this.customerType = customerType;
		}
		public AddressDetails() {
			super();
			// TODO Auto-generated constructor stub
		}
		public String getAddress1() {
			return address1;
		}
		public void setAddress1(String address1) {
			this.address1 = address1;
		}
		public String getAddress2() {
			return address2;
		}
		public void setAddress2(String address2) {
			this.address2 = address2;
		}
		public String getAddress3() {
			return address3;
		}
		public void setAddress3(String address3) {
			this.address3 = address3;
		}
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getPin() {
			return pin;
		}
		public void setPin(String pin) {
			this.pin = pin;
		}
		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}
		public String getGstNo() {
			return gstNo;
		}
		public void setGstNo(String gstNo) {
			this.gstNo = gstNo;
		}
		public String getCustomerType() {
			return customerType;
		}
		public void setCustomerType(String customerType) {
			this.customerType = customerType;
		}
		public String getDefaultChk() {
			return defaultChk;
		}
		public void setDefaultChk(String defaultChk) {
			this.defaultChk = defaultChk;
		}
		public int getSrNo() {
			return srNo;
		}
		public void setSrNo(int srNo) {
			this.srNo = srNo;
		}
		
		
		
	}
}
