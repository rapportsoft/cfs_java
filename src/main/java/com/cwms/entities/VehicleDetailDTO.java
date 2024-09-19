package com.cwms.entities;

import java.math.BigDecimal;

public class VehicleDetailDTO {

	public String vehId;
	public String vehicleNo;
	public String vehicleGatePassId;
	public int qtyTakenOut;
	public BigDecimal gwTakenOut;
	public VehicleDetailDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "VehicleDetailDTO [vehId=" + vehId + ", vehicleNo=" + vehicleNo + ", vehicleGatePassId="
				+ vehicleGatePassId + ", qtyTakenOut=" + qtyTakenOut + ", gwTakenOut=" + gwTakenOut + "]";
	}
	public VehicleDetailDTO(String vehId, String vehicleNo, String vehicleGatePassId, int qtyTakenOut,
			BigDecimal gwTakenOut) {
		super();
		this.vehId = vehId;
		this.vehicleNo = vehicleNo;
		this.vehicleGatePassId = vehicleGatePassId;
		this.qtyTakenOut = qtyTakenOut;
		this.gwTakenOut = gwTakenOut;
	}
	public String getVehId() {
		return vehId;
	}
	public void setVehId(String vehId) {
		this.vehId = vehId;
	}
	public String getVehicleNo() {
		return vehicleNo;
	}
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	public String getVehicleGatePassId() {
		return vehicleGatePassId;
	}
	public void setVehicleGatePassId(String vehicleGatePassId) {
		this.vehicleGatePassId = vehicleGatePassId;
	}
	public int getQtyTakenOut() {
		return qtyTakenOut;
	}
	public void setQtyTakenOut(int qtyTakenOut) {
		this.qtyTakenOut = qtyTakenOut;
	}
	public BigDecimal getGwTakenOut() {
		return gwTakenOut;
	}
	public void setGwTakenOut(BigDecimal gwTakenOut) {
		this.gwTakenOut = gwTakenOut;
	}
	
	
	
}
