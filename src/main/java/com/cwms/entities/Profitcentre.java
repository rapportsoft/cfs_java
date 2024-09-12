package com.cwms.entities;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "profitcentre")
@IdClass(ProfitcentreId.class) // Separate ID class for composite key
public class Profitcentre {

    @Id
    @Column(name = "Company_Id", length = 6, nullable = false)
    private String companyId;
    
    @Id
    @Column(name = "Branch_Id", length = 6, nullable = false)
    private String branchId;

    @Id
    @Column(name = "Profitcentre_Id", length = 6, nullable = false)
    private String profitcentreId;

    @Column(name = "Profitcentre_Desc", length = 35, nullable = false)
    private String profitcentreDesc;

    @Column(name = "Vessel_Mandatory", length = 1, nullable = false)
    private String vesselMandatory;

    @Column(name = "JO_Mandatory", length = 1, nullable = false)
    private String joMandatory;

    @Column(name = "Container_Mandatory", length = 1, nullable = false)
    private String containerMandatory;

    @Column(name = "Status", length = 1, nullable = false)
    private String status;

    @Column(name = "Created_By", length = 10, nullable = false)
    private String createdBy;

    @Column(name = "Created_Date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name = "Edited_By", length = 10)
    private String editedBy;

    @Column(name = "Edited_Date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date editedDate;

    @Column(name = "Approved_By", length = 10)
    private String approvedBy;

    @Column(name = "Approved_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date approvedDate;

    // Getters and Setters
    public String getCompanyId() {
        return companyId;
    }

    public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getProfitcentreId() {
        return profitcentreId;
    }

    public void setProfitcentreId(String profitcentreId) {
        this.profitcentreId = profitcentreId;
    }

    public String getProfitcentreDesc() {
        return profitcentreDesc;
    }

    public void setProfitcentreDesc(String profitcentreDesc) {
        this.profitcentreDesc = profitcentreDesc;
    }

    public String getVesselMandatory() {
        return vesselMandatory;
    }

    public void setVesselMandatory(String vesselMandatory) {
        this.vesselMandatory = vesselMandatory;
    }

    public String getJoMandatory() {
        return joMandatory;
    }

    public void setJoMandatory(String joMandatory) {
        this.joMandatory = joMandatory;
    }

    public String getContainerMandatory() {
        return containerMandatory;
    }

    public void setContainerMandatory(String containerMandatory) {
        this.containerMandatory = containerMandatory;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getEditedBy() {
        return editedBy;
    }

    public void setEditedBy(String editedBy) {
        this.editedBy = editedBy;
    }

    public Date getEditedDate() {
        return editedDate;
    }

    public void setEditedDate(Date editedDate) {
        this.editedDate = editedDate;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Date getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

	public Profitcentre() {
		
	}

	public Profitcentre(String companyId, String branchId, String profitcentreId, String profitcentreDesc,
			String vesselMandatory, String joMandatory, String containerMandatory, String status, String createdBy,
			Date createdDate, String editedBy, Date editedDate, String approvedBy, Date approvedDate) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.profitcentreId = profitcentreId;
		this.profitcentreDesc = profitcentreDesc;
		this.vesselMandatory = vesselMandatory;
		this.joMandatory = joMandatory;
		this.containerMandatory = containerMandatory;
		this.status = status;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.editedBy = editedBy;
		this.editedDate = editedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
	}
	
	
	

	public Profitcentre(String profitcentreId, String profitcentreDesc) {
		super();
		this.profitcentreId = profitcentreId;
		this.profitcentreDesc = profitcentreDesc;
	}

	@Override
	public String toString() {
		return "Profitcentre [companyId=" + companyId + ", profitcentreId=" + profitcentreId + ", profitcentreDesc="
				+ profitcentreDesc + ", vesselMandatory=" + vesselMandatory + ", joMandatory=" + joMandatory
				+ ", containerMandatory=" + containerMandatory + ", status=" + status + ", createdBy=" + createdBy
				+ ", createdDate=" + createdDate + ", editedBy=" + editedBy + ", editedDate=" + editedDate
				+ ", approvedBy=" + approvedBy + ", approvedDate=" + approvedDate + "]";
	}
}
