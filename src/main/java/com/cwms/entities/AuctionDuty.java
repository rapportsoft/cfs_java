package com.cwms.entities;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name="cfauccrg_duty")
@IdClass(AuctionDutyId.class)
public class AuctionDuty {
	
	    @Id
	    @Column(name = "Company_Id", length = 6)
	    private String companyId;

	    @Id
	    @Column(name = "Branch_Id", length = 6)
	    private String branchId;

	    @Id
	    @Column(name = "Notice_Id", length = 10)
	    private String noticeId;

	    @Id
	    @Column(name = "Duty_Type", length = 35)
	    private String dutyType;

	    @Id
	    @Column(name = "Notice_Amnd_No", length = 3)
	    private String noticeAmndNo;

	    @Id
	    @Column(name = "Final_Notice_Id", length = 10)
	    private String finalNoticeId;
	    
	    @Column(name = "IGM_Trans_Id", length = 10)
		private String igmTransId;
	    
	    @Column(name = "IGM_No", length = 10)
		private String igmNo;
	    
	    @Column(name = "IGM_Line_No", length = 7)
		private String igmLineNo;
	    
	    @Column(name = "Duty_Rate", precision = 15, scale = 2)
		private BigDecimal dutyRate;
	    
	    @Column(name = "Duty_Value", precision = 15, scale = 2)
		private BigDecimal dutyValue;
	    
	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name="Duty_Created_Date")
	    private Date dutyCreatedDate;
	    
	    @Column(name="Duty_Approved_By", length = 10)
	    private String dutyApprovedBy;
	    
	    @Column(name="Status", length = 1)
	    private String status;

	    public AuctionDuty() {
			super();
			// TODO Auto-generated constructor stub
		}

		

		public AuctionDuty(String companyId, String branchId, String noticeId, String dutyType, String noticeAmndNo,
				String finalNoticeId, String igmTransId, String igmNo, String igmLineNo, BigDecimal dutyRate,
				BigDecimal dutyValue, Date dutyCreatedDate, String dutyApprovedBy, String status) {
			super();
			this.companyId = companyId;
			this.branchId = branchId;
			this.noticeId = noticeId;
			this.dutyType = dutyType;
			this.noticeAmndNo = noticeAmndNo;
			this.finalNoticeId = finalNoticeId;
			this.igmTransId = igmTransId;
			this.igmNo = igmNo;
			this.igmLineNo = igmLineNo;
			this.dutyRate = dutyRate;
			this.dutyValue = dutyValue;
			this.dutyCreatedDate = dutyCreatedDate;
			this.dutyApprovedBy = dutyApprovedBy;
			this.status = status;
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

		public String getNoticeId() {
			return noticeId;
		}

		public void setNoticeId(String noticeId) {
			this.noticeId = noticeId;
		}

		public String getDutyType() {
			return dutyType;
		}

		public void setDutyType(String dutyType) {
			this.dutyType = dutyType;
		}

		public String getNoticeAmndNo() {
			return noticeAmndNo;
		}

		public void setNoticeAmndNo(String noticeAmndNo) {
			this.noticeAmndNo = noticeAmndNo;
		}

		public String getFinalNoticeId() {
			return finalNoticeId;
		}

		public void setFinalNoticeId(String finalNoticeId) {
			this.finalNoticeId = finalNoticeId;
		}

		public String getIgmTransId() {
			return igmTransId;
		}

		public void setIgmTransId(String igmTransId) {
			this.igmTransId = igmTransId;
		}

		public String getIgmNo() {
			return igmNo;
		}

		public void setIgmNo(String igmNo) {
			this.igmNo = igmNo;
		}

		public String getIgmLineNo() {
			return igmLineNo;
		}

		public void setIgmLineNo(String igmLineNo) {
			this.igmLineNo = igmLineNo;
		}

		public BigDecimal getDutyRate() {
			return dutyRate;
		}

		public void setDutyRate(BigDecimal dutyRate) {
			this.dutyRate = dutyRate;
		}

		public BigDecimal getDutyValue() {
			return dutyValue;
		}

		public void setDutyValue(BigDecimal dutyValue) {
			this.dutyValue = dutyValue;
		}

		public Date getDutyCreatedDate() {
			return dutyCreatedDate;
		}

		public void setDutyCreatedDate(Date dutyCreatedDate) {
			this.dutyCreatedDate = dutyCreatedDate;
		}

		public String getDutyApprovedBy() {
			return dutyApprovedBy;
		}

		public void setDutyApprovedBy(String dutyApprovedBy) {
			this.dutyApprovedBy = dutyApprovedBy;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}
	    
	    
	    

}
