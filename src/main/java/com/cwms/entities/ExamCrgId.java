package com.cwms.entities;

import java.io.Serializable;

public class ExamCrgId implements Serializable {
	private String companyId;

	private String branchId;

	private String finYear;

	private String examTallyId;

	private String examTallyLineId;

	public ExamCrgId() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ExamCrgId(String companyId, String branchId, String finYear, String examTallyId, String examTallyLineId) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.finYear = finYear;
		this.examTallyId = examTallyId;
		this.examTallyLineId = examTallyLineId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getFinYear() {
		return finYear;
	}

	public void setFinYear(String finYear) {
		this.finYear = finYear;
	}

	public String getExamTallyId() {
		return examTallyId;
	}

	public void setExamTallyId(String examTallyId) {
		this.examTallyId = examTallyId;
	}

	public String getExamTallyLineId() {
		return examTallyLineId;
	}

	public void setExamTallyLineId(String examTallyLineId) {
		this.examTallyLineId = examTallyLineId;
	}
	
	
	
}
