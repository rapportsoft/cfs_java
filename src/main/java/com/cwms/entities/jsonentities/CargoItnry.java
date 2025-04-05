package com.cwms.entities.jsonentities;

public class CargoItnry {

	private String documentType;
	private String modeOfTrnsprt;
	private String nxtPrtOfCallCdd;
	private String nxtPrtOfCallName;
	private String prtOfCallCdd;
	private String prtOfCallName;
	private int prtOfCallSeqNmbr;
	public CargoItnry() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CargoItnry(String documentType, String modeOfTrnsprt, String nxtPrtOfCallCdd, String nxtPrtOfCallName,
			String prtOfCallCdd, String prtOfCallName, int prtOfCallSeqNmbr) {
		super();
		this.documentType = documentType;
		this.modeOfTrnsprt = modeOfTrnsprt;
		this.nxtPrtOfCallCdd = nxtPrtOfCallCdd;
		this.nxtPrtOfCallName = nxtPrtOfCallName;
		this.prtOfCallCdd = prtOfCallCdd;
		this.prtOfCallName = prtOfCallName;
		this.prtOfCallSeqNmbr = prtOfCallSeqNmbr;
	}
	public String getDocumentType() {
		return documentType;
	}
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	public String getModeOfTrnsprt() {
		return modeOfTrnsprt;
	}
	public void setModeOfTrnsprt(String modeOfTrnsprt) {
		this.modeOfTrnsprt = modeOfTrnsprt;
	}
	public String getNxtPrtOfCallCdd() {
		return nxtPrtOfCallCdd;
	}
	public void setNxtPrtOfCallCdd(String nxtPrtOfCallCdd) {
		this.nxtPrtOfCallCdd = nxtPrtOfCallCdd;
	}
	public String getNxtPrtOfCallName() {
		return nxtPrtOfCallName;
	}
	public void setNxtPrtOfCallName(String nxtPrtOfCallName) {
		this.nxtPrtOfCallName = nxtPrtOfCallName;
	}
	public String getPrtOfCallCdd() {
		return prtOfCallCdd;
	}
	public void setPrtOfCallCdd(String prtOfCallCdd) {
		this.prtOfCallCdd = prtOfCallCdd;
	}
	public String getPrtOfCallName() {
		return prtOfCallName;
	}
	public void setPrtOfCallName(String prtOfCallName) {
		this.prtOfCallName = prtOfCallName;
	}
	public int getPrtOfCallSeqNmbr() {
		return prtOfCallSeqNmbr;
	}
	public void setPrtOfCallSeqNmbr(int prtOfCallSeqNmbr) {
		this.prtOfCallSeqNmbr = prtOfCallSeqNmbr;
	}
	
	
	
}
