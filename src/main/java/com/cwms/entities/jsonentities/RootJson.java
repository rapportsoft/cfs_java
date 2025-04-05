package com.cwms.entities.jsonentities;

import lombok.Data;

@Data
public class RootJson {

	private HeaderField headerField;
	private Master master;
	public RootJson() {
		super();
		// TODO Auto-generated constructor stub
	}
	public RootJson(HeaderField headerField, Master master) {
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
	public Master getMaster() {
		return master;
	}
	public void setMaster(Master master) {
		this.master = master;
	}
	
	
}
