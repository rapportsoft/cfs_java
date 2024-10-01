package com.cwms.entities;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name="cfcommongatepass")
@IdClass(CommonGatePassId.class)
public class CommonGatePass {

	@Id
	@Column(name = "Company_Id", length = 6)
	public String companyId;

	@Id
	@Column(name = "Branch_Id", length = 6)
	public String branchId;
	
	@Id
	@Column(name="Job_Trans_Id",length = 10)
	public String jobTransId;
	
	@Id
	@Column(name="Gate_Pass_Id",length = 10)
	public String gatePassId;
	
	@Id
	@Column(name = "Container_No", length = 11)
	public String containerNo;
	
	@Id
	@Column(name = "Sr_No")
	public int srNo;
	
	@Column(name="Job_Trans_Date")
	@Temporal(TemporalType.TIMESTAMP)
	public Date jobTransDate;
	
	@Column(name="Gate_Pass_Date")
	@Temporal(TemporalType.TIMESTAMP)
	public Date gatePassDate;
	
	@Column(name="sa",length = 6)
	public String sa;
	
	@Column(name="sl",length = 6)
	public String sl;
	
	@Column(name = "Vehicle_No", length = 15)
	private String vehicleNo;
	
	@Column(name = "Vehicle_Id", length = 10)
	private String vehicleId;

	@Column(name = "Driver_Name", length = 50)
	private String driverName;
	
	@Column(name="On_Account_Of",length = 6)
	public String onAccountOf;
	
	@Column(name="Booking_No",length = 15)
	public String bookingNo;
	
	@Column(name = "DO_No", length = 31)
	public String doNo = ""; // Default value

	@Column(name = "DO_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	public Date doDate;

	@Column(name = "DO_Validity_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	public Date doValidityDate;
	
	@Column(name="shipper",length = 6)
	public String shipper;
	
	@Column(name="Cha",length = 6)
	public String cha;

	@Column(name = "Profitcentre_Id", length = 6)
	public String profitcentreId;
	
	@Column(name="Movement_Type",length = 10)
	public String movementType;
	
	@Column(name = "Gate_In_Id", length = 10)
	public String gateInId = ""; // Default value

	@Column(name = "Gate_In_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	public Date gateInDate; // Default value
	
	@Column(name = "DeStuff_Id", length = 10)
	public String deStuffId = ""; // Default value
	
	@Column(name = "Container_Size", length = 6)
	public String containerSize = ""; // Default value

	@Column(name = "Container_Type", length = 6)
	public String containerType = ""; 
	
	@Column(name = "ISO", length = 4)
	public String iso = "";
	
	@Column(name = "Container_Status", length = 3)
	public String containerStatus = "";
	
	@Column(name="Tare_Wt",precision = 18,scale = 3)
	public BigDecimal tareWt;
	
	@Column(name = "Container_Health", length = 6, columnDefinition = "varchar(6) default ''")
	public String containerHealth = "";
	
    @Column(name = "From_Location", length = 50)
    public String fromLocation = "";
    
    @Column(name="Movement_Code",length = 6)
    public String movementCode;
    
    @Column(name="Status",length = 1)
    public String status;
    
    @Column(name="To_Location",length = 50)
    public String toLocation;
    
    @Column(name="Gate_Out_Id",length = 10)
    public String gateOutId;
    
	@Column(name = "Created_By", length = 10, columnDefinition = "varchar(10) default ''")
	public String createdBy = "";

	@Column(name = "Created_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	public Date createdDate;
	
	

	public CommonGatePass() {
		super();
		// TODO Auto-generated constructor stub
	}



	


	public CommonGatePass(String companyId, String branchId, String jobTransId, String gatePassId, String containerNo,
			int srNo, Date jobTransDate, Date gatePassDate, String sa, String sl, String vehicleNo, String vehicleId,
			String driverName, String onAccountOf, String bookingNo, String doNo, Date doDate, Date doValidityDate,
			String shipper, String cha, String profitcentreId, String movementType, String gateInId, Date gateInDate,
			String deStuffId, String containerSize, String containerType, String iso, String containerStatus,
			BigDecimal tareWt, String containerHealth, String fromLocation, String movementCode, String status,
			String toLocation, String gateOutId, String createdBy, Date createdDate) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.jobTransId = jobTransId;
		this.gatePassId = gatePassId;
		this.containerNo = containerNo;
		this.srNo = srNo;
		this.jobTransDate = jobTransDate;
		this.gatePassDate = gatePassDate;
		this.sa = sa;
		this.sl = sl;
		this.vehicleNo = vehicleNo;
		this.vehicleId = vehicleId;
		this.driverName = driverName;
		this.onAccountOf = onAccountOf;
		this.bookingNo = bookingNo;
		this.doNo = doNo;
		this.doDate = doDate;
		this.doValidityDate = doValidityDate;
		this.shipper = shipper;
		this.cha = cha;
		this.profitcentreId = profitcentreId;
		this.movementType = movementType;
		this.gateInId = gateInId;
		this.gateInDate = gateInDate;
		this.deStuffId = deStuffId;
		this.containerSize = containerSize;
		this.containerType = containerType;
		this.iso = iso;
		this.containerStatus = containerStatus;
		this.tareWt = tareWt;
		this.containerHealth = containerHealth;
		this.fromLocation = fromLocation;
		this.movementCode = movementCode;
		this.status = status;
		this.toLocation = toLocation;
		this.gateOutId = gateOutId;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
	}


    



	public String getGateOutId() {
		return gateOutId;
	}






	public void setGateOutId(String gateOutId) {
		this.gateOutId = gateOutId;
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



	public String getJobTransId() {
		return jobTransId;
	}



	public void setJobTransId(String jobTransId) {
		this.jobTransId = jobTransId;
	}



	public String getGatePassId() {
		return gatePassId;
	}



	public void setGatePassId(String gatePassId) {
		this.gatePassId = gatePassId;
	}



	public String getContainerNo() {
		return containerNo;
	}



	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
	}



	public int getSrNo() {
		return srNo;
	}



	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}



	public Date getJobTransDate() {
		return jobTransDate;
	}



	public void setJobTransDate(Date jobTransDate) {
		this.jobTransDate = jobTransDate;
	}



	public Date getGatePassDate() {
		return gatePassDate;
	}



	public void setGatePassDate(Date gatePassDate) {
		this.gatePassDate = gatePassDate;
	}



	public String getSa() {
		return sa;
	}



	public void setSa(String sa) {
		this.sa = sa;
	}



	public String getSl() {
		return sl;
	}



	public void setSl(String sl) {
		this.sl = sl;
	}



	public String getVehicleNo() {
		return vehicleNo;
	}



	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}



	public String getVehicleId() {
		return vehicleId;
	}



	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}



	public String getDriverName() {
		return driverName;
	}



	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}



	public String getOnAccountOf() {
		return onAccountOf;
	}



	public void setOnAccountOf(String onAccountOf) {
		this.onAccountOf = onAccountOf;
	}



	public String getBookingNo() {
		return bookingNo;
	}



	public void setBookingNo(String bookingNo) {
		this.bookingNo = bookingNo;
	}



	public String getDoNo() {
		return doNo;
	}



	public void setDoNo(String doNo) {
		this.doNo = doNo;
	}



	public Date getDoDate() {
		return doDate;
	}



	public void setDoDate(Date doDate) {
		this.doDate = doDate;
	}



	public Date getDoValidityDate() {
		return doValidityDate;
	}



	public void setDoValidityDate(Date doValidityDate) {
		this.doValidityDate = doValidityDate;
	}



	public String getShipper() {
		return shipper;
	}



	public void setShipper(String shipper) {
		this.shipper = shipper;
	}



	public String getCha() {
		return cha;
	}



	public void setCha(String cha) {
		this.cha = cha;
	}



	public String getProfitcentreId() {
		return profitcentreId;
	}



	public void setProfitcentreId(String profitcentreId) {
		this.profitcentreId = profitcentreId;
	}



	public String getMovementType() {
		return movementType;
	}



	public void setMovementType(String movementType) {
		this.movementType = movementType;
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



	public String getDeStuffId() {
		return deStuffId;
	}



	public void setDeStuffId(String deStuffId) {
		this.deStuffId = deStuffId;
	}



	public String getContainerSize() {
		return containerSize;
	}



	public void setContainerSize(String containerSize) {
		this.containerSize = containerSize;
	}



	public String getContainerType() {
		return containerType;
	}



	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}



	public String getIso() {
		return iso;
	}



	public void setIso(String iso) {
		this.iso = iso;
	}



	public String getContainerStatus() {
		return containerStatus;
	}



	public void setContainerStatus(String containerStatus) {
		this.containerStatus = containerStatus;
	}



	public BigDecimal getTareWt() {
		return tareWt;
	}



	public void setTareWt(BigDecimal tareWt) {
		this.tareWt = tareWt;
	}



	public String getContainerHealth() {
		return containerHealth;
	}



	public void setContainerHealth(String containerHealth) {
		this.containerHealth = containerHealth;
	}



	public String getFromLocation() {
		return fromLocation;
	}



	public void setFromLocation(String fromLocation) {
		this.fromLocation = fromLocation;
	}



	public String getMovementCode() {
		return movementCode;
	}



	public void setMovementCode(String movementCode) {
		this.movementCode = movementCode;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public String getToLocation() {
		return toLocation;
	}



	public void setToLocation(String toLocation) {
		this.toLocation = toLocation;
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
	
	
	
	
}
