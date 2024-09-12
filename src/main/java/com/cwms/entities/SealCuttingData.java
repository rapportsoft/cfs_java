package com.cwms.entities;

import java.util.List;

public class SealCuttingData {

	public Cfigmcrg crg;
	public List<Cfigmcn> cn;
	public SealCuttingData() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SealCuttingData(Cfigmcrg crg, List<Cfigmcn> cn) {
		super();
		this.crg = crg;
		this.cn = cn;
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
	
	
}
