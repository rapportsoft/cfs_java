package com.cwms.entities;

import java.io.Serializable;
import java.util.Objects;

public class GeneralGatePassGridId implements Serializable {

    private String deliveryId;
    
    private String gatePassId;

    private String boeNo;

    private int srNo;

    private String yardLocation;

    private String yardBlock;

    private String blockCellNo;

    private String companyId;

    private String branchId;

    public GeneralGatePassGridId() {}

    public GeneralGatePassGridId(String deliveryId,String gatePassId, String boeNo, int srNo, String yardLocation, String yardBlock, 
                                 String blockCellNo, String companyId, String branchId) {
    	this.gatePassId=gatePassId;
        this.deliveryId = deliveryId;
        this.boeNo = boeNo;
        this.srNo = srNo;
        this.yardLocation = yardLocation;
        this.yardBlock = yardBlock;
        this.blockCellNo = blockCellNo;
        this.companyId = companyId;
        this.branchId = branchId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeneralGatePassGridId that = (GeneralGatePassGridId) o;
        return Objects.equals(deliveryId, that.deliveryId) &&
        		 Objects.equals(gatePassId, that.gatePassId) &&
               Objects.equals(boeNo, that.boeNo) &&
               Objects.equals(srNo, that.srNo) &&
               Objects.equals(yardLocation, that.yardLocation) &&
               Objects.equals(yardBlock, that.yardBlock) &&
               Objects.equals(blockCellNo, that.blockCellNo) &&
               Objects.equals(companyId, that.companyId) &&
               Objects.equals(branchId, that.branchId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deliveryId,gatePassId, boeNo, srNo, yardLocation, yardBlock, blockCellNo, companyId, branchId);
    }
}

