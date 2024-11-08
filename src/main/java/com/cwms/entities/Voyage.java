package com.cwms.entities;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;

@Entity
@Table(name = "voyage")
@IdClass(VoyageId.class)
public class Voyage {

	@Id
	@Column(name = "company_id", length = 6)
	private String companyId;

	@Id
	@Column(name = "branch_id", length = 6)
	private String branchId;

	@Id
	@Column(name = "pod", length = 6)
	private String pod;

	@Id
	@Column(name = "pol", length = 6)
	private String pol;

	@Id
	@Column(name = "vessel_code", length = 10)
	private String vesselCode;

	@Id
	@Column(name = "voyage_no", length = 10)
	private String voyageNo;

	@Id
	@Column(name = "via_no", length = 10)
	private String viaNo;

	@Id
	@Column(name = "igm_no", length = 10)
	private String igmNo;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "voyage_sequence")
	@SequenceGenerator(name = "voyage_sequence", sequenceName = "voyage_sequence", allocationSize = 1)
	@Column(name = "sr_no")
	private int srNo;

	@Column(name = "party_id", length = 6)
	private String partyId;

	@Column(name = "vessel_schd_no", length = 10)
	private String vesselSchdNo;



	@Column(name = "igm_date")
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date igmDate;

	@Column(name = "eta")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date eta;

	@Column(name = "gate_open_date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date gateOpenDate;

	@Column(name = "vessel_arv_date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date vesselArvDate;

	@Column(name = "vessel_sail_date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date vesselSailDate;

	@Column(name = "atb")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date atb;

	@Column(name = "atd")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date atd;

	@Column(name = "load_date_time")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date loadDateTime;

	@Column(name = "cut_off_date_time")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date cutOffDateTime;

	@Column(name = "no_of_crew")
	private int noOfCrew = 0;

	@Column(name = "no_of_passenger")
	private int noOfPassenger = 0;

	@Column(name = "positionOfVessel", length = 35)
	private String positionOfVessel;

	@Column(name = "master_name", length = 35)
	private String masterName;

	@Column(name = "berth_no", length = 10)
	private String berthNo;

	@Column(name = "berth_date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date berthDate;

	@Column(name = "rotation_no", length = 10)
	private String rotationNo;

	@Column(name = "rotation_no_date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date rotationNoDate;

	@Column(name = "via_no_date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date viaNoDate;

	@Column(name = "pc_no", length = 10)
	private String pcNo;

	@Column(name = "pc_date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date pcdate;

	@Column(name = "light_house_cert_no", length = 10)
	private String lightHouseCertNo;

	@Column(name = "light_house_cert_date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date lightHouseCertDate;

	@Column(name = "light_dues", precision = 18, scale = 2)
	private BigDecimal lightDues;

	@Column(name = "Created_By", length = 10, nullable = false)
	private String createdBy = "";

	@Column(name = "Created_Date", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date createdDate;

	@Column(name = "Edited_By", length = 10)
	private String editedBy;

	@Column(name = "Edited_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date editedDate;

	@Column(name = "Approved_By", length = 10)
	private String approvedBy;

	@Column(name = "Approved_Date")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Date approvedDate;
	
	@Column(name="status",length = 1)
	private String status;
	
	@Transient
	private String vesselName;	
	
	

	public String getVesselName() {
		return vesselName;
	}

	public void setVesselName(String vesselName) {
		this.vesselName = vesselName;
	}

	public Voyage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Voyage(String companyId, String branchId, String pod, String pol, String vesselCode, String voyageNo,
			String viaNo, String igmNo, String partyId, String vesselSchdNo, int srNo, Date igmDate, Date eta,
			Date gateOpenDate, Date vesselArvDate, Date vesselSailDate, Date atb, Date atd, Date loadDateTime,
			Date cutOffDateTime, int noOfCrew, int noOfPassenger, String positionOfVessel, String masterName,
			String berthNo, Date berthDate, String rotationNo, Date rotationNoDate, Date viaNoDate, String pcNo,
			Date pcdate, String lightHouseCertNo, Date lightHouseCertDate, BigDecimal lightDues, String createdBy,
			Date createdDate, String editedBy, Date editedDate, String approvedBy, Date approvedDate, String status) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.pod = pod;
		this.pol = pol;
		this.vesselCode = vesselCode;
		this.voyageNo = voyageNo;
		this.viaNo = viaNo;
		this.igmNo = igmNo;
		this.partyId = partyId;
		this.vesselSchdNo = vesselSchdNo;
		this.srNo = srNo;
		this.igmDate = igmDate;
		this.eta = eta;
		this.gateOpenDate = gateOpenDate;
		this.vesselArvDate = vesselArvDate;
		this.vesselSailDate = vesselSailDate;
		this.atb = atb;
		this.atd = atd;
		this.loadDateTime = loadDateTime;
		this.cutOffDateTime = cutOffDateTime;
		this.noOfCrew = noOfCrew;
		this.noOfPassenger = noOfPassenger;
		this.positionOfVessel = positionOfVessel;
		this.masterName = masterName;
		this.berthNo = berthNo;
		this.berthDate = berthDate;
		this.rotationNo = rotationNo;
		this.rotationNoDate = rotationNoDate;
		this.viaNoDate = viaNoDate;
		this.pcNo = pcNo;
		this.pcdate = pcdate;
		this.lightHouseCertNo = lightHouseCertNo;
		this.lightHouseCertDate = lightHouseCertDate;
		this.lightDues = lightDues;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.editedBy = editedBy;
		this.editedDate = editedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
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

	public String getPod() {
		return pod;
	}

	public void setPod(String pod) {
		this.pod = pod;
	}

	public String getPol() {
		return pol;
	}

	public void setPol(String pol) {
		this.pol = pol;
	}

	public String getVesselCode() {
		return vesselCode;
	}

	public void setVesselCode(String vesselCode) {
		this.vesselCode = vesselCode;
	}

	public String getVoyageNo() {
		return voyageNo;
	}

	public void setVoyageNo(String voyageNo) {
		this.voyageNo = voyageNo;
	}

	public String getViaNo() {
		return viaNo;
	}

	public void setViaNo(String viaNo) {
		this.viaNo = viaNo;
	}

	public String getIgmNo() {
		return igmNo;
	}

	public void setIgmNo(String igmNo) {
		this.igmNo = igmNo;
	}

	public String getPartyId() {
		return partyId;
	}

	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}

	public String getVesselSchdNo() {
		return vesselSchdNo;
	}

	public void setVesselSchdNo(String vesselSchdNo) {
		this.vesselSchdNo = vesselSchdNo;
	}

	public int getSrNo() {
		return srNo;
	}

	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}

	public Date getIgmDate() {
		return igmDate;
	}

	public void setIgmDate(Date igmDate) {
		this.igmDate = igmDate;
	}

	public Date getEta() {
		return eta;
	}

	public void setEta(Date eta) {
		this.eta = eta;
	}

	public Date getGateOpenDate() {
		return gateOpenDate;
	}

	public void setGateOpenDate(Date gateOpenDate) {
		this.gateOpenDate = gateOpenDate;
	}

	public Date getVesselArvDate() {
		return vesselArvDate;
	}

	public void setVesselArvDate(Date vesselArvDate) {
		this.vesselArvDate = vesselArvDate;
	}

	public Date getVesselSailDate() {
		return vesselSailDate;
	}

	public void setVesselSailDate(Date vesselSailDate) {
		this.vesselSailDate = vesselSailDate;
	}

	public Date getAtb() {
		return atb;
	}

	public void setAtb(Date atb) {
		this.atb = atb;
	}

	public Date getAtd() {
		return atd;
	}

	public void setAtd(Date atd) {
		this.atd = atd;
	}

	public Date getLoadDateTime() {
		return loadDateTime;
	}

	public void setLoadDateTime(Date loadDateTime) {
		this.loadDateTime = loadDateTime;
	}

	public Date getCutOffDateTime() {
		return cutOffDateTime;
	}

	public void setCutOffDateTime(Date cutOffDateTime) {
		this.cutOffDateTime = cutOffDateTime;
	}

	public int getNoOfCrew() {
		return noOfCrew;
	}

	public void setNoOfCrew(int noOfCrew) {
		this.noOfCrew = noOfCrew;
	}

	public int getNoOfPassenger() {
		return noOfPassenger;
	}

	public void setNoOfPassenger(int noOfPassenger) {
		this.noOfPassenger = noOfPassenger;
	}

	public String getPositionOfVessel() {
		return positionOfVessel;
	}

	public void setPositionOfVessel(String positionOfVessel) {
		this.positionOfVessel = positionOfVessel;
	}

	public String getMasterName() {
		return masterName;
	}

	public void setMasterName(String masterName) {
		this.masterName = masterName;
	}

	public String getBerthNo() {
		return berthNo;
	}

	public void setBerthNo(String berthNo) {
		this.berthNo = berthNo;
	}

	public Date getBerthDate() {
		return berthDate;
	}

	public void setBerthDate(Date berthDate) {
		this.berthDate = berthDate;
	}

	public String getRotationNo() {
		return rotationNo;
	}

	public void setRotationNo(String rotationNo) {
		this.rotationNo = rotationNo;
	}

	public Date getRotationNoDate() {
		return rotationNoDate;
	}

	public void setRotationNoDate(Date rotationNoDate) {
		this.rotationNoDate = rotationNoDate;
	}

	public Date getViaNoDate() {
		return viaNoDate;
	}

	public void setViaNoDate(Date viaNoDate) {
		this.viaNoDate = viaNoDate;
	}

	public String getPcNo() {
		return pcNo;
	}

	public void setPcNo(String pcNo) {
		this.pcNo = pcNo;
	}

	public Date getPcdate() {
		return pcdate;
	}

	public void setPcdate(Date pcdate) {
		this.pcdate = pcdate;
	}

	public String getLightHouseCertNo() {
		return lightHouseCertNo;
	}

	public void setLightHouseCertNo(String lightHouseCertNo) {
		this.lightHouseCertNo = lightHouseCertNo;
	}

	public Date getLightHouseCertDate() {
		return lightHouseCertDate;
	}

	public void setLightHouseCertDate(Date lightHouseCertDate) {
		this.lightHouseCertDate = lightHouseCertDate;
	}

	public BigDecimal getLightDues() {
		return lightDues;
	}

	public void setLightDues(BigDecimal lightDues) {
		this.lightDues = lightDues;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Voyage(String pod, String pol, String vesselCode, String voyageNo, String viaNo, Date eta, Date gateOpenDate,
			Date atb, Date atd, Date cutOffDateTime, String status) {
		super();
		this.pod = pod;
		this.pol = pol;
		this.vesselCode = vesselCode;
		this.voyageNo = voyageNo;
		this.viaNo = viaNo;
		this.eta = eta;
		this.gateOpenDate = gateOpenDate;
		this.atb = atb;
		this.atd = atd;
		this.cutOffDateTime = cutOffDateTime;
		this.status = status;
	}

	@Override
	public String toString() {
		return "Voyage [companyId=" + companyId + ", branchId=" + branchId + ", pod=" + pod + ", pol=" + pol
				+ ", vesselCode=" + vesselCode + ", voyageNo=" + voyageNo + ", viaNo=" + viaNo + ", igmNo=" + igmNo
				+ ", partyId=" + partyId + ", vesselSchdNo=" + vesselSchdNo + ", srNo=" + srNo + ", igmDate=" + igmDate
				+ ", eta=" + eta + ", gateOpenDate=" + gateOpenDate + ", vesselArvDate=" + vesselArvDate
				+ ", vesselSailDate=" + vesselSailDate + ", atb=" + atb + ", atd=" + atd + ", loadDateTime="
				+ loadDateTime + ", cutOffDateTime=" + cutOffDateTime + ", noOfCrew=" + noOfCrew + ", noOfPassenger="
				+ noOfPassenger + ", positionOfVessel=" + positionOfVessel + ", masterName=" + masterName + ", berthNo="
				+ berthNo + ", berthDate=" + berthDate + ", rotationNo=" + rotationNo + ", rotationNoDate="
				+ rotationNoDate + ", viaNoDate=" + viaNoDate + ", pcNo=" + pcNo + ", pcdate=" + pcdate
				+ ", lightHouseCertNo=" + lightHouseCertNo + ", lightHouseCertDate=" + lightHouseCertDate
				+ ", lightDues=" + lightDues + ", createdBy=" + createdBy + ", createdDate=" + createdDate
				+ ", editedBy=" + editedBy + ", editedDate=" + editedDate + ", approvedBy=" + approvedBy
				+ ", approvedDate=" + approvedDate + ", status=" + status + "]";
	}
	
	

//	Voyage search
	public Voyage(String vesselCode, String voyageNo, String viaNo, Date gateOpenDate, Date berthDate,
			String rotationNo, Date rotationNoDate, String vesselName) {
		this.vesselCode = vesselCode;
		this.voyageNo = voyageNo;
		this.viaNo = viaNo;
		this.gateOpenDate = gateOpenDate;
		this.berthDate = berthDate;
		this.rotationNo = rotationNo;
		this.rotationNoDate = rotationNoDate;
		this.vesselName = vesselName;
	}


}
