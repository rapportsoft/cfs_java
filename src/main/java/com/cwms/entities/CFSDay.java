package com.cwms.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name="cfsday")
@IdClass(BranchId.class)
public class CFSDay {

	@Id
	@Column(name="Company_Id",length = 6)
    private String companyId;
    
    @Id
    @Column(name="Bracnh_Id",length = 6)
    private String branchId;
    
    @Column(name="Start_Time")
    @Temporal(TemporalType.TIME)
    private Date startTime;
    
    @Column(name="End_Time")
    @Temporal(TemporalType.TIME)
    private Date endTime;

	public CFSDay() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CFSDay(String companyId, String branchId, Date startTime, Date endTime) {
		this.companyId = companyId;
		this.branchId = branchId;
		this.startTime = startTime;
		this.endTime = endTime;
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

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
    
    
    

}
