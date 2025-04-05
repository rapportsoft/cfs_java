package com.cwms.entities.jsonentities;

public class RootJson1 {
	private HeaderField headerField;
	private Master1 master;
	public RootJson1() {
		super();
		// TODO Auto-generated constructor stub
	}
	public RootJson1(HeaderField headerField, Master1 master) {
		super();
		this.headerField = headerField;
		this.master = master;
	}
	public HeaderField getHeaderField() {
		return headerField;
	}
	public void setHeaderField(HeaderField headerField) {
		this.headerField = headerField;
	}
	public Master1 getMaster() {
		return master;
	}
	public void setMaster(Master1 master) {
		this.master = master;
	}
	
	
}
