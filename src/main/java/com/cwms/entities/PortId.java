package com.cwms.entities;

import java.io.Serializable;
import java.util.Objects;

public class PortId implements Serializable {

	private String companyId;
	private String branchId;
    private String portCode;
    private String portTransId;

	public PortId() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	

	public String getCompanyId() {
		return companyId;
	}

	public String getPortTransId() {
		return portTransId;
	}




	public void setPortTransId(String portTransId) {
		this.portTransId = portTransId;
	}




	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getPortCode() {
		return portCode;
	}

	public void setPortCode(String portCode) {
		this.portCode = portCode;
	}


	public String getBranchId() {
		return branchId;
	}


	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}




	@Override
	public int hashCode() {
		return Objects.hash(branchId, companyId, portCode, portTransId);
	}




	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PortId other = (PortId) obj;
		return Objects.equals(branchId, other.branchId) && Objects.equals(companyId, other.companyId)
				&& Objects.equals(portCode, other.portCode) && Objects.equals(portTransId, other.portTransId);
	}


}
