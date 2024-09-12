package com.cwms.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

public class YardBlockCellID implements Serializable {

    private String companyId;
    private String yardId;
    private String yardLocationId;
    private String blockId;
    private String cellNoRow;
    private String yardTransId;

	
	public YardBlockCellID(String companyId, String yardId, String yardLocationId, String blockId, String cellNoRow,
			String yardTransId) {
		super();
		this.companyId = companyId;
		this.yardId = yardId;
		this.yardLocationId = yardLocationId;
		this.blockId = blockId;
		this.cellNoRow = cellNoRow;
		this.yardTransId = yardTransId;
	}

	
	
	public String getYardTransId() {
		return yardTransId;
	}



	public void setYardTransId(String yardTransId) {
		this.yardTransId = yardTransId;
	}



	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getYardId() {
		return yardId;
	}

	public void setYardId(String yardId) {
		this.yardId = yardId;
	}

	public String getYardLocationId() {
		return yardLocationId;
	}

	public void setYardLocationId(String yardLocationId) {
		this.yardLocationId = yardLocationId;
	}

	public String getBlockId() {
		return blockId;
	}

	public void setBlockId(String blockId) {
		this.blockId = blockId;
	}

	public String getCellNoRow() {
		return cellNoRow;
	}

	public void setCellNoRow(String cellNoRow) {
		this.cellNoRow = cellNoRow;
	}

	@Override
	public String toString() {
		return "YardBlockCellID [companyId=" + companyId + ", yardId=" + yardId + ", yardLocationId=" + yardLocationId
				+ ", blockId=" + blockId + ", cellNoRow=" + cellNoRow + "]";
	}

	public YardBlockCellID() {
		super();
		// TODO Auto-generated constructor stub
	}

    
    
}
