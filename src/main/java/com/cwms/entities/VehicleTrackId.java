package com.cwms.entities;

import java.io.Serializable;

public class VehicleTrackId implements Serializable {
	private String companyId;

	private String branchId;

	private String finYear;

	private String vehicleNo;

	private String profitcentreId;

	private int srNo;

	private String gateInId;

	public VehicleTrackId() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public VehicleTrackId(String companyId, String branchId, String finYear, String vehicleNo, String profitcentreId,
			int srNo, String gateInId) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.finYear = finYear;
		this.vehicleNo = vehicleNo;
		this.profitcentreId = profitcentreId;
		this.srNo = srNo;
		this.gateInId = gateInId;
	}

	


	public String getGateInId() {
		return gateInId;
	}



	public void setGateInId(String gateInId) {
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

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getProfitcentreId() {
		return profitcentreId;
	}

	public void setProfitcentreId(String profitcentreId) {
		this.profitcentreId = profitcentreId;
	}

	public int getSrNo() {
		return srNo;
	}

	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}

}
