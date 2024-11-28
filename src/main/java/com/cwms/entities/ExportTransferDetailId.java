package com.cwms.entities;

import java.io.Serializable;

public class ExportTransferDetailId  implements Serializable{

	private String companyId;

    private String branchId;

    private String finYear;

    private String sbChangeTransId;

    private String transLineId;

    private String cartingTransId;

    private String cartingLineId;

    private String sbTransId;

    private String vehicleNo;

    private String gateInId;

	public ExportTransferDetailId() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ExportTransferDetailId(String companyId, String branchId, String finYear, String sbChangeTransId,
			String transLineId, String cartingTransId, String cartingLineId, String sbTransId, String vehicleNo,
			String gateInId) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.finYear = finYear;
		this.sbChangeTransId = sbChangeTransId;
		this.transLineId = transLineId;
		this.cartingTransId = cartingTransId;
		this.cartingLineId = cartingLineId;
		this.sbTransId = sbTransId;
		this.vehicleNo = vehicleNo;
		this.gateInId = gateInId;
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

	public String getSbChangeTransId() {
		return sbChangeTransId;
	}

	public void setSbChangeTransId(String sbChangeTransId) {
		this.sbChangeTransId = sbChangeTransId;
	}

	public String getTransLineId() {
		return transLineId;
	}

	public void setTransLineId(String transLineId) {
		this.transLineId = transLineId;
	}

	public String getCartingTransId() {
		return cartingTransId;
	}

	public void setCartingTransId(String cartingTransId) {
		this.cartingTransId = cartingTransId;
	}

	public String getCartingLineId() {
		return cartingLineId;
	}

	public void setCartingLineId(String cartingLineId) {
		this.cartingLineId = cartingLineId;
	}

	public String getSbTransId() {
		return sbTransId;
	}

	public void setSbTransId(String sbTransId) {
		this.sbTransId = sbTransId;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getGateInId() {
		return gateInId;
	}

	public void setGateInId(String gateInId) {
		this.gateInId = gateInId;
	}

    
    
}
