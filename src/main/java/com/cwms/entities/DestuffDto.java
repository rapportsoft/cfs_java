package com.cwms.entities;

import java.util.Date;
import java.util.List;

public class DestuffDto {

	public Destuff destuff;
	public List<DestuffCrg> crg;
	public String doNo;
	public String oocNo;
	public Date oocDate;
	public Date doDate;
	public Date doValidityDate;
	
	
	public String getDoNo() {
		return doNo;
	}
	public void setDoNo(String doNo) {
		this.doNo = doNo;
	}
	public String getOocNo() {
		return oocNo;
	}
	public void setOocNo(String oocNo) {
		this.oocNo = oocNo;
	}
	public Date getOocDate() {
		return oocDate;
	}
	public void setOocDate(Date oocDate) {
		this.oocDate = oocDate;
	}
	public Date getDoDate() {
		return doDate;
	}
	public void setDoDate(Date doDate) {
		this.doDate = doDate;
	}
	public Date getDoValidityDate() {
		return doValidityDate;
	}
	public void setDoValidityDate(Date doValidityDate) {
		this.doValidityDate = doValidityDate;
	}
	public DestuffDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DestuffDto(Destuff destuff, List<DestuffCrg> crg) {
		super();
		this.destuff = destuff;
		this.crg = crg;
	}
	public Destuff getDestuff() {
		return destuff;
	}
	public void setDestuff(Destuff destuff) {
		this.destuff = destuff;
	}
	public List<DestuffCrg> getCrg() {
		return crg;
	}
	public void setCrg(List<DestuffCrg> crg) {
		this.crg = crg;
	}
	@Override
	public String toString() {
		return "DestuffDto [destuff=" + destuff + ", crg=" + crg + "]";
	}
	
	
	
}
