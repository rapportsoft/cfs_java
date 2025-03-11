package com.cwms.entities;

import java.io.Serializable;

public class GeneralReceivingCrgId implements Serializable {

    private String companyId;
    
    private String branchId;
    
    private String receivingId;
    
    private String depositNo;
    
    private String importerName;

	public GeneralReceivingCrgId(String companyId, String branchId, String receivingId, String depositNo,
			String importerName) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.receivingId = receivingId;
		this.depositNo = depositNo;
		this.importerName = importerName;
	}

	public GeneralReceivingCrgId() {
		super();
		// TODO Auto-generated constructor stub
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

	public String getReceivingId() {
		return receivingId;
	}

	public void setReceivingId(String receivingId) {
		this.receivingId = receivingId;
	}

	public String getDepositNo() {
		return depositNo;
	}

	public void setDepositNo(String depositNo) {
		this.depositNo = depositNo;
	}

	public String getImporterName() {
		return importerName;
	}

	public void setImporterName(String importerName) {
		this.importerName = importerName;
	}
    
    
    
}
