package com.cwms.entities;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.cwms.entities.ContainerSealCuttingDTO.Cargo;
import com.cwms.entities.ContainerSealCuttingDTO.Container;

public class ContainerExaminationDTO {
	private Container con;
	private List<Cargo> cargo;
	public String doNo;
	public String oocNo;
	public Date oocDate;
	public Date doDate;
	public Date doValidityDate;
	
	
	
	

	
	
	
	
	public String getDoNo() {
		return doNo;
	}


	public void setDoNo(String doNo) {
		this.doNo = doNo;
	}


	public String getOocNo() {
		return oocNo;
	}


	public void setOocNo(String oocNo) {
		this.oocNo = oocNo;
	}


	public Date getOocDate() {
		return oocDate;
	}


	public void setOocDate(Date oocDate) {
		this.oocDate = oocDate;
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


	public Container getCon() {
		return con;
	}


	public void setCon(Container con) {
		this.con = con;
	}


	


	public List<Cargo> getCargo() {
		return cargo;
	}


	public void setCargo(List<Cargo> cargo) {
		this.cargo = cargo;
	}


	

	



	public ContainerExaminationDTO(Container con, List<Cargo> cargo) {
		super();
		this.con = con;
		this.cargo = cargo;
	}








	public static class Container{
		 private String igmNo;
		    private String igmTransId;
		    private String igmLineNo;
		    private String containerNo;
		    private String gateInId;
		    private String containerTransId;
		    private String partyName;
		    private String viaNo;
		    private String mobileNo;
		    private String gateOutType;
		    private String containerSize;
		    private String containerType;
		    private String customsSealNo;
		    private BigDecimal grossWt; // Assuming weight is in double
		    private Date gateInDate;
		    private Date sealCutWoTransDate;
		    private BigDecimal weighmentWeight; // Assuming weight is in double
		    private String sealCuttingType;
		    private String sealCuttingStatus;
		    private String sealCutWoTransId;
		    private String cycle;
		    private String vehicleType;
		    private String sealCutRemarks;
		    private String notifyPartyName;
		    private String scanningDoneStatus;
		    private String odcStatus;
		    private String lowBed;
		    private String status;
		    private int noOfPackages;
		    private String containerExamWoTransId;
		    private Date containerExamWoTransDate;
		    private Date containerExamDate;
		    private int examinedPackages;
		    private int packagesDeStuffed;
		    private int packagesStuffed;
		    private String typeOfContainer;
		    private String containerExamRemarks;
		    private String specialDelivery;
		    private String scannerType;
		    private String containerExamStatus;
		    
		    
		    
		    
		    
		    
			public int getNoOfPackages() {
				return noOfPackages;
			}
			public void setNoOfPackages(int noOfPackages) {
				this.noOfPackages = noOfPackages;
			}
			public String getContainerExamWoTransId() {
				return containerExamWoTransId;
			}
			public void setContainerExamWoTransId(String containerExamWoTransId) {
				this.containerExamWoTransId = containerExamWoTransId;
			}
			public Date getContainerExamWoTransDate() {
				return containerExamWoTransDate;
			}
			public void setContainerExamWoTransDate(Date containerExamWoTransDate) {
				this.containerExamWoTransDate = containerExamWoTransDate;
			}
			public Date getContainerExamDate() {
				return containerExamDate;
			}
			public void setContainerExamDate(Date containerExamDate) {
				this.containerExamDate = containerExamDate;
			}
			public int getExaminedPackages() {
				return examinedPackages;
			}
			public void setExaminedPackages(int examinedPackages) {
				this.examinedPackages = examinedPackages;
			}
			public int getPackagesDeStuffed() {
				return packagesDeStuffed;
			}
			public void setPackagesDeStuffed(int packagesDeStuffed) {
				this.packagesDeStuffed = packagesDeStuffed;
			}
			public int getPackagesStuffed() {
				return packagesStuffed;
			}
			public void setPackagesStuffed(int packagesStuffed) {
				this.packagesStuffed = packagesStuffed;
			}
			public String getTypeOfContainer() {
				return typeOfContainer;
			}
			public void setTypeOfContainer(String typeOfContainer) {
				this.typeOfContainer = typeOfContainer;
			}
			public String getContainerExamRemarks() {
				return containerExamRemarks;
			}
			public void setContainerExamRemarks(String containerExamRemarks) {
				this.containerExamRemarks = containerExamRemarks;
			}
			public String getSpecialDelivery() {
				return specialDelivery;
			}
			public void setSpecialDelivery(String specialDelivery) {
				this.specialDelivery = specialDelivery;
			}
			public String getScannerType() {
				return scannerType;
			}
			public void setScannerType(String scannerType) {
				this.scannerType = scannerType;
			}
			public String getContainerExamStatus() {
				return containerExamStatus;
			}
			public void setContainerExamStatus(String containerExamStatus) {
				this.containerExamStatus = containerExamStatus;
			}
			public String getStatus() {
				return status;
			}
			public void setStatus(String status) {
				this.status = status;
			}
			public String getIgmNo() {
				return igmNo;
			}
			public void setIgmNo(String igmNo) {
				this.igmNo = igmNo;
			}
			public String getIgmTransId() {
				return igmTransId;
			}
			public void setIgmTransId(String igmTransId) {
				this.igmTransId = igmTransId;
			}
			public String getIgmLineNo() {
				return igmLineNo;
			}
			public void setIgmLineNo(String igmLineNo) {
				this.igmLineNo = igmLineNo;
			}
			public String getContainerNo() {
				return containerNo;
			}
			public void setContainerNo(String containerNo) {
				this.containerNo = containerNo;
			}
			public String getGateInId() {
				return gateInId;
			}
			public void setGateInId(String gateInId) {
				this.gateInId = gateInId;
			}
			public String getContainerTransId() {
				return containerTransId;
			}
			public void setContainerTransId(String containerTransId) {
				this.containerTransId = containerTransId;
			}
			public String getPartyName() {
				return partyName;
			}
			public void setPartyName(String partyName) {
				this.partyName = partyName;
			}
			public String getViaNo() {
				return viaNo;
			}
			public void setViaNo(String viaNo) {
				this.viaNo = viaNo;
			}
			public String getMobileNo() {
				return mobileNo;
			}
			public void setMobileNo(String mobileNo) {
				this.mobileNo = mobileNo;
			}
			public String getGateOutType() {
				return gateOutType;
			}
			public void setGateOutType(String gateOutType) {
				this.gateOutType = gateOutType;
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
			public String getCustomsSealNo() {
				return customsSealNo;
			}
			public void setCustomsSealNo(String customsSealNo) {
				this.customsSealNo = customsSealNo;
			}
			public BigDecimal getGrossWt() {
				return grossWt;
			}
			public void setGrossWt(BigDecimal grossWt) {
				this.grossWt = grossWt;
			}
			public Date getGateInDate() {
				return gateInDate;
			}
			public void setGateInDate(Date gateInDate) {
				this.gateInDate = gateInDate;
			}
			public Date getSealCutWoTransDate() {
				return sealCutWoTransDate;
			}
			public void setSealCutWoTransDate(Date sealCutWoTransDate) {
				this.sealCutWoTransDate = sealCutWoTransDate;
			}
			public BigDecimal getWeighmentWeight() {
				return weighmentWeight;
			}
			public void setWeighmentWeight(BigDecimal weighmentWeight) {
				this.weighmentWeight = weighmentWeight;
			}
			public String getSealCuttingType() {
				return sealCuttingType;
			}
			public void setSealCuttingType(String sealCuttingType) {
				this.sealCuttingType = sealCuttingType;
			}
			public String getSealCuttingStatus() {
				return sealCuttingStatus;
			}
			public void setSealCuttingStatus(String sealCuttingStatus) {
				this.sealCuttingStatus = sealCuttingStatus;
			}
			public String getSealCutWoTransId() {
				return sealCutWoTransId;
			}
			public void setSealCutWoTransId(String sealCutWoTransId) {
				this.sealCutWoTransId = sealCutWoTransId;
			}
			public String getCycle() {
				return cycle;
			}
			public void setCycle(String cycle) {
				this.cycle = cycle;
			}
			public String getVehicleType() {
				return vehicleType;
			}
			public void setVehicleType(String vehicleType) {
				this.vehicleType = vehicleType;
			}
			public String getSealCutRemarks() {
				return sealCutRemarks;
			}
			public void setSealCutRemarks(String sealCutRemarks) {
				this.sealCutRemarks = sealCutRemarks;
			}
			public String getNotifyPartyName() {
				return notifyPartyName;
			}
			public void setNotifyPartyName(String notifyPartyName) {
				this.notifyPartyName = notifyPartyName;
			}
			public String getScanningDoneStatus() {
				return scanningDoneStatus;
			}
			public void setScanningDoneStatus(String scanningDoneStatus) {
				this.scanningDoneStatus = scanningDoneStatus;
			}
			public String getOdcStatus() {
				return odcStatus;
			}
			public void setOdcStatus(String odcStatus) {
				this.odcStatus = odcStatus;
			}
			public String getLowBed() {
				return lowBed;
			}
			public void setLowBed(String lowBed) {
				this.lowBed = lowBed;
			}
			public Container(String igmNo, String igmTransId, String igmLineNo, String containerNo, String gateInId,
					String containerTransId, String partyName, String viaNo, String mobileNo, String gateOutType,
					String containerSize, String containerType, String customsSealNo, BigDecimal grossWt,
					Date gateInDate, Date sealCutWoTransDate, BigDecimal weighmentWeight, String sealCuttingType,
					String sealCuttingStatus, String sealCutWoTransId, String cycle, String vehicleType,
					String sealCutRemarks, String notifyPartyName, String scanningDoneStatus, String odcStatus,
					String lowBed, String status) {
				super();
				this.igmNo = igmNo;
				this.igmTransId = igmTransId;
				this.igmLineNo = igmLineNo;
				this.containerNo = containerNo;
				this.gateInId = gateInId;
				this.containerTransId = containerTransId;
				this.partyName = partyName;
				this.viaNo = viaNo;
				this.mobileNo = mobileNo;
				this.gateOutType = gateOutType;
				this.containerSize = containerSize;
				this.containerType = containerType;
				this.customsSealNo = customsSealNo;
				this.grossWt = grossWt;
				this.gateInDate = gateInDate;
				this.sealCutWoTransDate = sealCutWoTransDate;
				this.weighmentWeight = weighmentWeight;
				this.sealCuttingType = sealCuttingType;
				this.sealCuttingStatus = sealCuttingStatus;
				this.sealCutWoTransId = sealCutWoTransId;
				this.cycle = cycle;
				this.vehicleType = vehicleType;
				this.sealCutRemarks = sealCutRemarks;
				this.notifyPartyName = notifyPartyName;
				this.scanningDoneStatus = scanningDoneStatus;
				this.odcStatus = odcStatus;
				this.lowBed = lowBed;
				this.status = status;
			}
			
		    
		    
	}
	
	
	public static class Cargo{
	    private String igmNo;
	    private String igmTransId;
	    private String igmLineNo;
	    private String importerName;
	    private String blNo;
	    private Date blDate;
	    private int noOfPackages;
	    private BigDecimal grossWeight;
	    private String beNo;
	    private Date beDate;
	    private BigDecimal cargoValue;
	    private BigDecimal cargoDuty;
	    private String blType;
	    private String commodityDescription;
	    private String chaCode;
	    private String chaName;
		public Cargo(String igmNo, String igmTransId, String igmLineNo, String importerName, String blNo, Date blDate,
				int noOfPackages, BigDecimal grossWeight, String beNo, Date beDate, BigDecimal cargoValue,
				BigDecimal cargoDuty, String blType, String commodityDescription, String chaCode, String chaName) {
			super();
			this.igmNo = igmNo;
			this.igmTransId = igmTransId;
			this.igmLineNo = igmLineNo;
			this.importerName = importerName;
			this.blNo = blNo;
			this.blDate = blDate;
			this.noOfPackages = noOfPackages;
			this.grossWeight = grossWeight;
			this.beNo = beNo;
			this.beDate = beDate;
			this.cargoValue = cargoValue;
			this.cargoDuty = cargoDuty;
			this.blType = blType;
			this.commodityDescription = commodityDescription;
			this.chaCode = chaCode;
			this.chaName = chaName;
		}
		public String getIgmNo() {
			return igmNo;
		}
		public void setIgmNo(String igmNo) {
			this.igmNo = igmNo;
		}
		public String getIgmTransId() {
			return igmTransId;
		}
		public void setIgmTransId(String igmTransId) {
			this.igmTransId = igmTransId;
		}
		public String getIgmLineNo() {
			return igmLineNo;
		}
		public void setIgmLineNo(String igmLineNo) {
			this.igmLineNo = igmLineNo;
		}
		public String getImporterName() {
			return importerName;
		}
		public void setImporterName(String importerName) {
			this.importerName = importerName;
		}
		public String getBlNo() {
			return blNo;
		}
		public void setBlNo(String blNo) {
			this.blNo = blNo;
		}
		public Date getBlDate() {
			return blDate;
		}
		public void setBlDate(Date blDate) {
			this.blDate = blDate;
		}
		public int getNoOfPackages() {
			return noOfPackages;
		}
		public void setNoOfPackages(int noOfPackages) {
			this.noOfPackages = noOfPackages;
		}
		public BigDecimal getGrossWeight() {
			return grossWeight;
		}
		public void setGrossWeight(BigDecimal grossWeight) {
			this.grossWeight = grossWeight;
		}
		public String getBeNo() {
			return beNo;
		}
		public void setBeNo(String beNo) {
			this.beNo = beNo;
		}
		public Date getBeDate() {
			return beDate;
		}
		public void setBeDate(Date beDate) {
			this.beDate = beDate;
		}
		public BigDecimal getCargoValue() {
			return cargoValue;
		}
		public void setCargoValue(BigDecimal cargoValue) {
			this.cargoValue = cargoValue;
		}
		public BigDecimal getCargoDuty() {
			return cargoDuty;
		}
		public void setCargoDuty(BigDecimal cargoDuty) {
			this.cargoDuty = cargoDuty;
		}
		public String getBlType() {
			return blType;
		}
		public void setBlType(String blType) {
			this.blType = blType;
		}
		public String getCommodityDescription() {
			return commodityDescription;
		}
		public void setCommodityDescription(String commodityDescription) {
			this.commodityDescription = commodityDescription;
		}
		public String getChaCode() {
			return chaCode;
		}
		public void setChaCode(String chaCode) {
			this.chaCode = chaCode;
		}
		public String getChaName() {
			return chaName;
		}
		public void setChaName(String chaName) {
			this.chaName = chaName;
		}
}
}