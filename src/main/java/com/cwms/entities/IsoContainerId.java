package com.cwms.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

public class IsoContainerId implements Serializable{
	@Id
    @Column(name = "Company_Id", length = 10, nullable = false)
    private String companyId;

    @Id
    @Column(name = "ISO_Code", length = 4, nullable = false)
    private String isoCode;

	public IsoContainerId() {
		super();
	}
    
}
