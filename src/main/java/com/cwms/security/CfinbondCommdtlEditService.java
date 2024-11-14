package com.cwms.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cwms.repository.CfinbondCommdtlEditRepository;

@Service
public class CfinbondCommdtlEditService {

	@Autowired
	public CfinbondCommdtlEditRepository cfinbondCommdtlEditRepository;
}
