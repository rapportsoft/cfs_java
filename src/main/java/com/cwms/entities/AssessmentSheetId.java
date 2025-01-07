package com.cwms.entities;

import java.io.Serializable;

public class AssessmentSheetId implements Serializable {

    private String assesmentId;
    private String companyId;
    private String branchId;
    private String assesmentLineNo;
    private String transType;
	public AssessmentSheetId() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AssessmentSheetId(String assesmentId, String companyId, String branchId, String assesmentLineNo,
			String transType) {
		this.assesmentId = assesmentId;
		this.companyId = companyId;
		this.branchId = branchId;
		this.assesmentLineNo = assesmentLineNo;
		this.transType = transType;
	}
	public String getAssesmentId() {
		return assesmentId;
	}
	public void setAssesmentId(String assesmentId) {
		this.assesmentId = assesmentId;
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
	public String getAssesmentLineNo() {
		return assesmentLineNo;
	}
	public void setAssesmentLineNo(String assesmentLineNo) {
		this.assesmentLineNo = assesmentLineNo;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
    
    
    
}
