package com.cwms.entities;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ExportMovementId implements Serializable {

    private String companyId;

    private String branchId;

    private String finYear;

    private String movementReqId;

    private String movementReqLineId;

    // Default constructor
    public ExportMovementId() {}

    // Parameterized constructor
    public ExportMovementId(String companyId, String branchId, String finYear, String movementReqId, String movementReqLineId) {
        this.companyId = companyId;
        this.branchId = branchId;
        this.finYear = finYear;
        this.movementReqId = movementReqId;
        this.movementReqLineId = movementReqLineId;
    }

    // Getters and Setters
    public String getCompanyId() { return companyId; }
    public void setCompanyId(String companyId) { this.companyId = companyId; }

    public String getBranchId() { return branchId; }
    public void setBranchId(String branchId) { this.branchId = branchId; }

    public String getFinYear() { return finYear; }
    public void setFinYear(String finYear) { this.finYear = finYear; }

    public String getMovementReqId() { return movementReqId; }
    public void setMovementReqId(String movementReqId) { this.movementReqId = movementReqId; }

    public String getMovementReqLineId() { return movementReqLineId; }
    public void setMovementReqLineId(String movementReqLineId) { this.movementReqLineId = movementReqLineId; }

    // Override equals and hashCode for composite key comparison
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExportMovementId that = (ExportMovementId) o;
        return Objects.equals(companyId, that.companyId) &&
                Objects.equals(branchId, that.branchId) &&
                Objects.equals(finYear, that.finYear) &&
                Objects.equals(movementReqId, that.movementReqId) &&
                Objects.equals(movementReqLineId, that.movementReqLineId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyId, branchId, finYear, movementReqId, movementReqLineId);
    }
}
