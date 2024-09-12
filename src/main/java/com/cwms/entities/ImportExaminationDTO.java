package com.cwms.entities;

import java.util.Date;
import java.util.List;

public class ImportExaminationDTO {

	public Cfigmcrg crg;
	public List<Cfigmcn> cn;
	public String doNo;
	public String oocNo;
	public Date oocDate;
	public Date doDate;
	public Date doValidityDate;
	public ImportExaminationDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ImportExaminationDTO(Cfigmcrg crg, List<Cfigmcn> cn, String doNo, String oocNo, Date oocDate, Date doDate,
			Date doValidityDate) {
		super();
		this.crg = crg;
		this.cn = cn;
		this.doNo = doNo;
		this.oocNo = oocNo;
		this.oocDate = oocDate;
		this.doDate = doDate;
		this.doValidityDate = doValidityDate;
	}
	public Cfigmcrg getCrg() {
		return crg;
	}
	public void setCrg(Cfigmcrg crg) {
		this.crg = crg;
	}
	public List<Cfigmcn> getCn() {
		return cn;
	}
	public void setCn(List<Cfigmcn> cn) {
		this.cn = cn;
	}
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
	
	
	
}
