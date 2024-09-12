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
@Table(name="cfvehtrck")
@IdClass(VehicleTrackId.class)
public class VehicleTrack {
	   @Id
	    @Column(name = "Company_Id", length = 6)
	    private String companyId;

	    @Id
	    @Column(name = "Branch_Id", length = 6)
	    private String branchId;

	    @Id
	    @Column(name = "Fin_Year", length = 4)
	    private String finYear;

	    @Id
	    @Column(name = "Vehicle_No", length = 15)
	    private String vehicleNo;

	    @Id
	    @Column(name = "Profitcentre_Id", length = 6)
	    private String profitcentreId;

	    @Id
	    @Column(name = "Sr_No")
	    private int srNo;
	    
	    @Id
	    @Column(name = "Gate_In_Id", length = 10)
	    private String gateInId;

	    @Column(name = "Transporter_Status", length = 1)
	    private char transporterStatus;

	    @Column(name = "Transporter_Name", length = 50)
	    private String transporterName;

	    @Column(name = "Transporter", length = 60)
	    private String transporter;

	    @Column(name = "Driver_Name", length = 50)
	    private String driverName;

	    @Column(name = "Vehicle_Status", length = 1)
	    private char vehicleStatus;

	    @Column(name = "IGM_Trans_Id", length = 10)
	    private String igmTransId;

	    @Column(name = "IGM_No", length = 15)
	    private String igmNo;

	    @Column(name = "BL_No", length = 15)
	    private String blNo;

	    @Column(name = "SB_No", length = 10)
	    private String sbNo;

	    @Column(name = "SB_Trans_Id", length = 10)
	    private String sbTransId;

	    

	    @Column(name = "Gate_In_Date")
	    @Temporal(TemporalType.TIMESTAMP)
	    private Date gateInDate;

	    @Column(name = "Gate_No_In", length = 6)
	    private String gateNoIn;

	    @Column(name = "Shift_In", length = 100)
	    private String shiftIn;

	    @Column(name = "Gate_Out_Id", length = 10)
	    private String gateOutId;

	    @Column(name = "Gate_Out_Date")
	    @Temporal(TemporalType.TIMESTAMP)
	    private Date gateOutDate;

	    @Column(name = "Gate_Pass_No", length = 10)
	    private String gatePassNo;

	    @Column(name = "Gate_No_Out", length = 6)
	    private String gateNoOut;

	    @Column(name = "Shift_Out", length = 100)
	    private String shiftOut;

	    @Column(name = "Container_No", length = 11)
	    private String containerNo;

	    @Column(name = "Container_Type", length = 6)
	    private String containerType;

	    @Column(name = "Container_Size", length = 6)
	    private String containerSize;

	    @Column(name = "Commodity_Id", length = 6)
	    private String commodityId;

	    @Column(name = "No_Of_Packages", precision = 8, scale = 0)
	    private BigDecimal noOfPackages;

	    @Column(name = "COMMENTS", length = 250)
	    private String comments;

	    @Column(name = "Created_By", length = 10)
	    private String createdBy;

	    @Column(name = "Created_Date")
	    @Temporal(TemporalType.TIMESTAMP)
	    private Date createdDate;

	    @Column(name = "Approved_By", length = 10)
	    private String approvedBy;

	    @Column(name = "Approved_Date")
	    @Temporal(TemporalType.TIMESTAMP)
	    private Date approvedDate;
	    
	    @Column(name = "Edited_By", length = 10)
	    private String editedBy;

	    @Column(name = "Edited_Date")
	    @Temporal(TemporalType.TIMESTAMP)
	    private Date editedDate;

	    @Column(name = "Status", length = 1)
	    private char status;

		public VehicleTrack() {
			super();
			// TODO Auto-generated constructor stub
		}

		

		public VehicleTrack(String companyId, String branchId, String finYear, String vehicleNo, String profitcentreId,
				int srNo, String gateInId, char transporterStatus, String transporterName, String transporter,
				String driverName, char vehicleStatus, String igmTransId, String igmNo, String blNo, String sbNo,
				String sbTransId, Date gateInDate, String gateNoIn, String shiftIn, String gateOutId, Date gateOutDate,
				String gatePassNo, String gateNoOut, String shiftOut, String containerNo, String containerType,
				String containerSize, String commodityId, BigDecimal noOfPackages, String comments, String createdBy,
				Date createdDate, String approvedBy, Date approvedDate, String editedBy, Date editedDate, char status) {
			super();
			this.companyId = companyId;
			this.branchId = branchId;
			this.finYear = finYear;
			this.vehicleNo = vehicleNo;
			this.profitcentreId = profitcentreId;
			this.srNo = srNo;
			this.gateInId = gateInId;
			this.transporterStatus = transporterStatus;
			this.transporterName = transporterName;
			this.transporter = transporter;
			this.driverName = driverName;
			this.vehicleStatus = vehicleStatus;
			this.igmTransId = igmTransId;
			this.igmNo = igmNo;
			this.blNo = blNo;
			this.sbNo = sbNo;
			this.sbTransId = sbTransId;
			this.gateInDate = gateInDate;
			this.gateNoIn = gateNoIn;
			this.shiftIn = shiftIn;
			this.gateOutId = gateOutId;
			this.gateOutDate = gateOutDate;
			this.gatePassNo = gatePassNo;
			this.gateNoOut = gateNoOut;
			this.shiftOut = shiftOut;
			this.containerNo = containerNo;
			this.containerType = containerType;
			this.containerSize = containerSize;
			this.commodityId = commodityId;
			this.noOfPackages = noOfPackages;
			this.comments = comments;
			this.createdBy = createdBy;
			this.createdDate = createdDate;
			this.approvedBy = approvedBy;
			this.approvedDate = approvedDate;
			this.editedBy = editedBy;
			this.editedDate = editedDate;
			this.status = status;
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

		public char getTransporterStatus() {
			return transporterStatus;
		}

		public void setTransporterStatus(char transporterStatus) {
			this.transporterStatus = transporterStatus;
		}

		public String getTransporterName() {
			return transporterName;
		}

		public void setTransporterName(String transporterName) {
			this.transporterName = transporterName;
		}

		public String getTransporter() {
			return transporter;
		}

		public void setTransporter(String transporter) {
			this.transporter = transporter;
		}

		public String getDriverName() {
			return driverName;
		}

		public void setDriverName(String driverName) {
			this.driverName = driverName;
		}

		public char getVehicleStatus() {
			return vehicleStatus;
		}

		public void setVehicleStatus(char vehicleStatus) {
			this.vehicleStatus = vehicleStatus;
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

		public String getBlNo() {
			return blNo;
		}

		public void setBlNo(String blNo) {
			this.blNo = blNo;
		}

		public String getSbNo() {
			return sbNo;
		}

		public void setSbNo(String sbNo) {
			this.sbNo = sbNo;
		}

		public String getSbTransId() {
			return sbTransId;
		}

		public void setSbTransId(String sbTransId) {
			this.sbTransId = sbTransId;
		}

		public String getGateInId() {
			return gateInId;
		}

		public void setGateInId(String gateInId) {
			this.gateInId = gateInId;
		}

		public Date getGateInDate() {
			return gateInDate;
		}

		public void setGateInDate(Date gateInDate) {
			this.gateInDate = gateInDate;
		}

		public String getGateNoIn() {
			return gateNoIn;
		}

		public void setGateNoIn(String gateNoIn) {
			this.gateNoIn = gateNoIn;
		}

		public String getShiftIn() {
			return shiftIn;
		}

		public void setShiftIn(String shiftIn) {
			this.shiftIn = shiftIn;
		}

		public String getGateOutId() {
			return gateOutId;
		}

		public void setGateOutId(String gateOutId) {
			this.gateOutId = gateOutId;
		}

		public Date getGateOutDate() {
			return gateOutDate;
		}

		public void setGateOutDate(Date gateOutDate) {
			this.gateOutDate = gateOutDate;
		}

		public String getGatePassNo() {
			return gatePassNo;
		}

		public void setGatePassNo(String gatePassNo) {
			this.gatePassNo = gatePassNo;
		}

		public String getGateNoOut() {
			return gateNoOut;
		}

		public void setGateNoOut(String gateNoOut) {
			this.gateNoOut = gateNoOut;
		}

		public String getShiftOut() {
			return shiftOut;
		}

		public void setShiftOut(String shiftOut) {
			this.shiftOut = shiftOut;
		}

		public String getContainerNo() {
			return containerNo;
		}

		public void setContainerNo(String containerNo) {
			this.containerNo = containerNo;
		}

		public String getContainerType() {
			return containerType;
		}

		public void setContainerType(String containerType) {
			this.containerType = containerType;
		}

		public String getContainerSize() {
			return containerSize;
		}

		public void setContainerSize(String containerSize) {
			this.containerSize = containerSize;
		}

		public String getCommodityId() {
			return commodityId;
		}

		public void setCommodityId(String commodityId) {
			this.commodityId = commodityId;
		}

		public BigDecimal getNoOfPackages() {
			return noOfPackages;
		}

		public void setNoOfPackages(BigDecimal noOfPackages) {
			this.noOfPackages = noOfPackages;
		}

		public String getComments() {
			return comments;
		}

		public void setComments(String comments) {
			this.comments = comments;
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

		public char getStatus() {
			return status;
		}

		public void setStatus(char status) {
			this.status = status;
		}
	    
	    

}
