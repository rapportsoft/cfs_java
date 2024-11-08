package com.cwms.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

public class ExportStuffTallyId implements Serializable {

    private String companyId;

    private String branchId;

    private String stuffTallyId;

    private String sbTransId;

    private int stuffTallyLineId;

    private String profitcentreId;


    private String cartingTransId;

    private String sbLineId;

    private String cartingLineId;

    private String sbNo;

	public ExportStuffTallyId() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ExportStuffTallyId(String companyId, String branchId, String stuffTallyId, String sbTransId,
			int stuffTallyLineId, String profitcentreId, String cartingTransId, String sbLineId, String cartingLineId,
			String sbNo) {
		this.companyId = companyId;
		this.branchId = branchId;
		this.stuffTallyId = stuffTallyId;
		this.sbTransId = sbTransId;
		this.stuffTallyLineId = stuffTallyLineId;
		this.profitcentreId = profitcentreId;
		this.cartingTransId = cartingTransId;
		this.sbLineId = sbLineId;
		this.cartingLineId = cartingLineId;
		this.sbNo = sbNo;
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

	public String getStuffTallyId() {
		return stuffTallyId;
	}

	public void setStuffTallyId(String stuffTallyId) {
		this.stuffTallyId = stuffTallyId;
	}

	public String getSbTransId() {
		return sbTransId;
	}

	public void setSbTransId(String sbTransId) {
		this.sbTransId = sbTransId;
	}

	public int getStuffTallyLineId() {
		return stuffTallyLineId;
	}

	public void setStuffTallyLineId(int stuffTallyLineId) {
		this.stuffTallyLineId = stuffTallyLineId;
	}

	public String getProfitcentreId() {
		return profitcentreId;
	}

	public void setProfitcentreId(String profitcentreId) {
		this.profitcentreId = profitcentreId;
	}

	public String getCartingTransId() {
		return cartingTransId;
	}

	public void setCartingTransId(String cartingTransId) {
		this.cartingTransId = cartingTransId;
	}

	public String getSbLineId() {
		return sbLineId;
	}

	public void setSbLineId(String sbLineId) {
		this.sbLineId = sbLineId;
	}

	public String getCartingLineId() {
		return cartingLineId;
	}

	public void setCartingLineId(String cartingLineId) {
		this.cartingLineId = cartingLineId;
	}

	public String getSbNo() {
		return sbNo;
	}

	public void setSbNo(String sbNo) {
		this.sbNo = sbNo;
	}
    
    
    
}
