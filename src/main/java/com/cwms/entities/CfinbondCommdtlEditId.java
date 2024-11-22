package com.cwms.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CfinbondCommdtlEditId implements Serializable {


	private Long SrNo;
	
    private String branchId;


    private String commodityId;


    private String commonBondingId;


    private String companyId;

    
    private String nocNo;


    private String nocTransId;
    
    private String auditId;

    // Constructors
    public CfinbondCommdtlEditId() {
    }



	public CfinbondCommdtlEditId(Long srNo, String branchId, String commodityId, String commonBondingId,
			String companyId, String nocNo, String nocTransId, String auditId) {
		super();
		SrNo = srNo;
		this.branchId = branchId;
		this.commodityId = commodityId;
		this.commonBondingId = commonBondingId;
		this.companyId = companyId;
		this.nocNo = nocNo;
		this.nocTransId = nocTransId;
		this.auditId = auditId;
	}



	public String getAuditId() {
		return auditId;
	}



	public void setAuditId(String auditId) {
		this.auditId = auditId;
	}



	public Long getSrNo() {
		return SrNo;
	}

	public void setSrNo(Long srNo) {
		SrNo = srNo;
	}

	// Getters and Setters
    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public String getCommonBondingId() {
        return commonBondingId;
    }

    public void setCommonBondingId(String commonBondingId) {
        this.commonBondingId = commonBondingId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getNocNo() {
        return nocNo;
    }

    public void setNocNo(String nocNo) {
        this.nocNo = nocNo;
    }

    public String getNocTransId() {
        return nocTransId;
    }

    public void setNocTransId(String nocTransId) {
        this.nocTransId = nocTransId;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        CfinbondCommdtlEditId that = (CfinbondCommdtlEditId) o;
//        return Objects.equals(branchId, that.branchId) &&
//                Objects.equals(commodityId, that.commodityId) &&
//                Objects.equals(commonBondingId, that.commonBondingId) &&
//                Objects.equals(companyId, that.companyId) &&
//                Objects.equals(nocNo, that.nocNo) &&
//                Objects.equals(nocTransId, that.nocTransId);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(branchId, commodityId, commonBondingId, companyId, nocNo, nocTransId);
//    }
}
