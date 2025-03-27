package com.cwms.helper;



public class FileResponseDTO {
    private String fileName;
    private String base64Url;
    private String fileType;
    private String companyId;
    private String branchId;
    private String sbTransId;
    private String sbLineNo;
    private String sbNo;
    private String hSbTransId;
    private String isSaved;
  
	public FileResponseDTO(String fileName, String base64Url, String fileType, String companyId,
			String branchId, String sbTransId, String sbLineNo, String sbNo,String hSbTransId, String isSaved) {
		super();
		this.fileName = fileName;
		this.base64Url = base64Url;
		this.fileType = fileType;
		this.companyId = companyId;
		this.branchId = branchId;
		this.sbTransId = sbTransId;
		this.sbLineNo = sbLineNo;
		this.sbNo = sbNo;
		this.hSbTransId = hSbTransId;
		this.isSaved = isSaved;
	}			

	public String getIsSaved() {
		return isSaved;
	}

	public void setIsSaved(String isSaved) {
		this.isSaved = isSaved;
	}



	public FileResponseDTO() {
		super();				
	}

	public String getCompanyId() {
		return companyId;
	}


	public String gethSbTransId() {
		return hSbTransId;
	}


	public void sethSbTransId(String hSbTransId) {
		this.hSbTransId = hSbTransId;
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


	public String getSbTransId() {
		return sbTransId;
	}


	public void setSbTransId(String sbTransId) {
		this.sbTransId = sbTransId;
	}


	public String getSbLineNo() {
		return sbLineNo;
	}


	public void setSbLineNo(String sbLineNo) {
		this.sbLineNo = sbLineNo;
	}


	public String getSbNo() {
		return sbNo;
	}


	public void setSbNo(String sbNo) {
		this.sbNo = sbNo;
	}


	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getBase64Url() {
        return base64Url;
    }

    public void setBase64Url(String base64Url) {
        this.base64Url = base64Url;
    }
}
