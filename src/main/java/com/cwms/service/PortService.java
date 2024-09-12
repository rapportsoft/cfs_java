package com.cwms.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cwms.entities.Port;
import com.cwms.repository.PortRepository;

@Service
public class PortService {

	@Autowired
	private PortRepository portRepo;

	@Autowired
	private ProcessNextIdService processService;

	public List<Port> searchPorts(String companyId, String branchId, String portCode, String portType,
			String portName) {

		return portRepo.searchPorts(companyId, branchId, portCode, portType, portName);
	}
	
	
	public Port getSinglePort(String companyId, String branchId, String portCode, String portTransId) {

		return portRepo.getPortByPortCodeAndTransId(companyId, branchId, portCode,portTransId);
	}
	
	
	public List<Port> getSelectTags(String companyId, String branchId) {

		return portRepo.getSelectTags(companyId, branchId);
	}

	public boolean checkduplicatePortNo(String companyId, String branchId, String portCode) {

		return portRepo.existsByCompanyIdAndBranchIdAndPortCodeAndStatusNotD(companyId, branchId, portCode);

	}

	public Port addPort(Port receivedPort, String user) {

		String autoIncrementPortTransId = processService.autoIncrementPortTransId(receivedPort.getCompanyId(), receivedPort.getBranchId(), "P05059");
		receivedPort.setPortTransId(autoIncrementPortTransId);
		receivedPort.setCreatedBy(user);
		receivedPort.setCreatedDate(new Date());
		receivedPort.setStatus("A");
		receivedPort.setApprovedBy(user);
		receivedPort.setApprovedDate(new Date());
		
		System.out.println(receivedPort);

		return portRepo.save(receivedPort);
	}

	public Port updatePort(Port receivedPort, String user) {
	
		receivedPort.setEditedBy(user);
		receivedPort.setEditedDate(new Date());
		return portRepo.save(receivedPort);
	}

	public Port deletePort(String companyId, String branchId, String portCode, String portTransId, String user) {	
		
		Port portByPortCodeAndTransId = portRepo.getPortByPortCodeAndTransId(companyId, branchId, portCode, portTransId);
		
		portByPortCodeAndTransId.setStatus("D");
		portByPortCodeAndTransId.setEditedBy(user);
		portByPortCodeAndTransId.setEditedDate(new Date());
		return portRepo.save(portByPortCodeAndTransId);
	}

	
	private List<Map<String, String>> convertToValueLabelList(List<String> data) {
	    return data.stream().map(obj -> {
	        Map<String, String> map = new HashMap<>();
	        map.put("value", obj);
	        map.put("label", obj);
	        return map;
	    }).collect(Collectors.toList());
	}
	
	
	public List<Map<String, String>> getPortToSelect(String companyId, String branchId, String portType)
	{
		
		
		List<String> portIdList = portRepo.getPortIdList(companyId, branchId, portType);
		 List<Map<String, String>> portList = convertToValueLabelList(portIdList); 
		
		return portList;
	}
}
