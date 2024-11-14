package com.cwms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cwms.security.CfinbondCommdtlEditService;

@RestController
@RequestMapping("/api/cfinbondCommdtlEdit")
@CrossOrigin("*")
public class CfinbondCommdtlEditController {

	@Autowired
	public CfinbondCommdtlEditService cfinbondCommdtlEditService;
}
