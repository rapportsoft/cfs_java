package com.cwms.entities;

public class MpcinDto {

	public String sbNo;
	public String sbTrasnId;
	public String mpcinNo;
	public String check;
	
	public MpcinDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MpcinDto(String sbNo, String sbTrasnId, String mpcinNo, String check) {
		super();
		this.sbNo = sbNo;
		this.sbTrasnId = sbTrasnId;
		this.mpcinNo = mpcinNo;
		this.check = check;
	}

	public String getSbNo() {
		return sbNo;
	}

	public void setSbNo(String sbNo) {
		this.sbNo = sbNo;
	}

	public String getSbTrasnId() {
		return sbTrasnId;
	}

	public void setSbTrasnId(String sbTrasnId) {
		this.sbTrasnId = sbTrasnId;
	}

	public String getMpcinNo() {
		return mpcinNo;
	}

	public void setMpcinNo(String mpcinNo) {
		this.mpcinNo = mpcinNo;
	}

	public String getCheck() {
		return check;
	}

	public void setCheck(String check) {
		this.check = check;
	}

	@Override
	public String toString() {
		return "MpcinDto [sbNo=" + sbNo + ", sbTrasnId=" + sbTrasnId + ", mpcinNo=" + mpcinNo + ", check=" + check
				+ "]";
	}
	
}
